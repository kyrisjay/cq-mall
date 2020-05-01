package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsAdminDao;
import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.UmsRoleDao;
import club.banyuan.mgt.dao.entity.*;
import club.banyuan.mgt.dto.*;
import club.banyuan.mgt.security.AdminUserDetails;
import club.banyuan.mgt.security.ResourceConfigAttribute;
import club.banyuan.mgt.service.AdminService;
import club.banyuan.mgt.service.CacheService;
import club.banyuan.mgt.service.TokenService;
import club.banyuan.mgt.service.UmsResourceService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.*;
import static club.banyuan.mgt.service.CacheKey.MALL_ADMIN;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String SCHEMA = "Bearer";
    private static final String TOKEN_HEAD_KEY = "Authorization";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UmsAdminDao umsAdminDao;

    @Autowired
    private UmsRoleDao umsRoleDao;

    @Autowired
    private UmsMenuDao umsMenuDao;

    @Autowired
    private UmsResourceService umsResourceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CacheService cacheService;

    @Override
    public AdminLoginResp login(AdminLoginReq adminLoginReq) {
        AdminLoginResp adminLoginResp = new AdminLoginResp();
        String username = adminLoginReq.getUsername();
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = umsAdminDao.selectByExample(example);
        if (CollUtil.isEmpty(umsAdmins) || !passwordEncoder
                .matches(adminLoginReq.getPassword(), umsAdmins.get(0).getPassword())) {
            throw new RequestFailException(UMS_ADMIN_USER_NOT_VALID);
        }

        adminLoginResp.setToken(tokenService.generateToken(umsAdmins.get(0).getId().toString()));
        adminLoginResp.setTokenHead(SCHEMA);

        return adminLoginResp;
    }

    @Override
    public UserDetails getUserDetailsByToken(String token) {
        long adminId = Long.parseLong(tokenService.parseSubject(token));

        UmsAdmin umsAdmin = umsAdminDao.selectByPrimaryKey(adminId);
        if (umsAdmin == null) {
            throw new RequestFailException(UMS_ADMIN_USER_NOT_EXIST);
        }

        List<UmsResource> adminResources = umsResourceService.getResourcesByAdminId(adminId);
        List<ResourceConfigAttribute> grantedAuthorities = new ArrayList<>();
        if (CollUtil.isNotEmpty(adminResources)) {
            adminResources.forEach(t -> grantedAuthorities.add(new ResourceConfigAttribute(t)));
        }

        return new AdminUserDetails(umsAdmin, grantedAuthorities);
    }

    @Cacheable(value = MALL_ADMIN, key = "#adminId")
    @Override
    public AdminInfoResp getInfo(long adminId) {
        UmsAdmin umsAdmin = cacheService.get(MALL_ADMIN + adminId);
        if (umsAdmin == null) {
            umsAdmin = umsAdminDao.selectByPrimaryKey(adminId);
        }

        AdminInfoResp adminInfoResp = new AdminInfoResp();
        adminInfoResp.setIcon(umsAdmin.getIcon());
        adminInfoResp.setUsername(umsAdmin.getUsername());

        List<UmsRole> umsRoleList = umsRoleDao.selectByAdminId(umsAdmin.getId());
        if (CollUtil.isEmpty(umsRoleList)) {
            throw new RequestFailException(UMS_ADMIN_ROLE_EMPTY);
        }

        List<UmsMenu> umsMenus = umsMenuDao
                .selectByRoleIds(umsRoleList.stream().map(UmsRole::getId).collect(Collectors.toList()));
        adminInfoResp.setMenus(umsMenus.stream().map(t -> {
            AdminMenusResp adminMenusResp = new AdminMenusResp();
            BeanUtil.copyProperties(t, adminMenusResp);
            return adminMenusResp;
        }).collect(Collectors.toList()));

        adminInfoResp.setRoles(umsRoleList.stream().map(UmsRole::getName).collect(Collectors.toList()));
        return adminInfoResp;
    }

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = umsAdminExample.createCriteria();
        UmsAdminExample.Criteria criteria1 = umsAdminExample.createCriteria();
        if (StrUtil.isNotBlank(keyword)) {
            criteria.andNickNameLike(StrUtil.concat(true, "%", keyword, "%"));
            criteria1.andUsernameLike(StrUtil.concat(true, "%", keyword, "%"));
        }
        umsAdminExample.or(criteria1);
        List<UmsAdmin> umsAdmins = umsAdminDao.selectByExample(umsAdminExample);
        PageInfo<UmsAdmin> pageInfo = new PageInfo<>(umsAdmins);
        List<UmsAdminResp> umsAdminRespList = umsAdmins.stream().distinct().map(t -> {
            UmsAdminResp umsAdminResp = new UmsAdminResp();
            BeanUtil.copyProperties(t, umsAdminResp);
            return umsAdminResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, umsAdminRespList);
    }

    @Override
    public List<UmsRoleResp> getRoleByAdminId(Long adminId) {

        List<UmsRole> umsRoles = umsRoleDao.selectByAdminId(adminId);

        return umsRoles.stream().map(t -> {
            UmsRoleResp umsRoleResp = new UmsRoleResp();
            BeanUtil.copyProperties(t, umsRoleResp);
            return umsRoleResp;
        }).collect(Collectors.toList());
    }

    @Override
    public int update(UmsAdmin umsAdmin, long adminId) throws ParseException {
        UmsAdmin umsAdmin1 = umsAdminDao.selectByPrimaryKey(adminId);
        if (ObjectUtil.isEmpty(umsAdmin1)) {
            throw new RequestFailException(UMS_ADMIN_ROLE_EMPTY);
        }
//        String createTime = umsAdmin.getCreateTime();
//        String loginTime = umsAdmin.getLoginTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

//java.util.Date对象
//        Date createTimeFM = (Date) sdf.parse(createTime);
//        Date loginTimeFM = (Date) sdf.parse(loginTime);
//        UmsAdmin umsAdminResp = new UmsAdmin();
//        umsAdminResp.setCreateTime(createTimeFM);
//        umsAdminResp.setEmail(umsAdmin.getEmail());
//        umsAdminResp.setIcon(umsAdmin.getIcon());
//        umsAdminResp.setLoginTime(loginTimeFM);
//        umsAdminResp.setNickName(umsAdmin.getNickName());
//        umsAdminResp.setNote(umsAdmin.getNote());
//        umsAdminResp.setPassword(umsAdmin.getPassword());
//        umsAdminResp.setStatus(umsAdmin.getStatus());
//        umsAdminResp.setUsername(umsAdmin.getUsername());
//        umsAdminResp.setId(adminId);
        umsAdmin.setId(adminId);
        return umsAdminDao.updateByPrimaryKey(umsAdmin);
    }

    @Override
    public Long delete(Long adminId) {
        List<UmsRole> umsRoleList = umsRoleDao.selectByAdminId(adminId);
        if (CollUtil.isEmpty(umsRoleList)) {
            throw new RequestFailException(UMS_ADMIN_ROLE_EMPTY);
        }
        umsAdminDao.deleteByPrimaryKey(adminId);
        return adminId;
    }

    @Override
    public Integer updateRole(Long adminId, List<Long> roleIds) {
        UmsAdmin umsAdmin = umsAdminDao.selectByPrimaryKey(adminId);
        if (ObjectUtil.isEmpty(umsAdmin)) {
            throw new RequestFailException(UMS_ADMIN_ROLE_EMPTY);
        }
        umsRoleDao.deleteAdminROleRelationByAdminId(adminId);
        umsRoleDao.insertAdminRoleRelationByAdminId(adminId, roleIds);

        return roleIds.size();
    }

    @Override
    public UmsAdminResp register(UmsAdmin admin) {
        List<UmsAdmin> umsAdmins = umsAdminDao.selectAll();

        umsAdmins.forEach(t -> {
            if (t.getUsername().equals(admin.getUsername())) {
                throw new RequestFailException(UMS_ADMIN_NAME_DUPLICATE);
            }
        });
        admin.setCreateTime(new Date());
        umsAdminDao.insert(admin);
        UmsAdminResp umsAdminResp = new UmsAdminResp();
        BeanUtil.copyProperties(admin, umsAdminResp);
        return umsAdminResp;
    }


//    @CacheEvict(value = MALL_ADMIN, allEntries = true)
//    public void updateMenu(){
//
//    }
}
