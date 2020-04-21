package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.dao.UmsAdminDao;
import club.banyuan.mgt.dao.UmsMenuDao;
import club.banyuan.mgt.dao.UmsRoleDao;
import club.banyuan.mgt.dao.entity.*;
import club.banyuan.mgt.dto.AdminInfoResp;
import club.banyuan.mgt.dto.AdminLoginReq;
import club.banyuan.mgt.dto.AdminLoginResp;
import club.banyuan.mgt.dto.AdminMenusResp;
import club.banyuan.mgt.security.AdminUserDetails;
import club.banyuan.mgt.security.ResourceConfigAttribute;
import club.banyuan.mgt.service.AdminService;
import club.banyuan.mgt.service.TokenService;
import club.banyuan.mgt.service.UmsResourceService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public AdminLoginResp login(AdminLoginReq adminLoginReq) {
        AdminLoginResp adminLoginResp = new AdminLoginResp();
        String username = adminLoginReq.getUsername();
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = umsAdminDao.selectByExample(example);
        if (CollUtil.isEmpty(umsAdmins) || !passwordEncoder
                .matches(adminLoginReq.getPassword(), umsAdmins.get(0).getPassword())){
            throw new RuntimeException("用户名或者密码错误");
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
            throw new RuntimeException("用户不存在");
        }

        List<UmsResource> adminResources = umsResourceService.getResourcesByAdminId(adminId);
        List<ResourceConfigAttribute> grantedAuthorities = new ArrayList<>();
        if (CollUtil.isNotEmpty(adminResources)) {
            adminResources.forEach(t -> grantedAuthorities.add(new ResourceConfigAttribute(t)));
        }
        return new AdminUserDetails(umsAdmin, grantedAuthorities);
    }

    @Override
    public AdminInfoResp getInfo(long adminId) {
        UmsAdmin umsAdmin = umsAdminDao.selectByPrimaryKey(adminId);

        AdminInfoResp adminInfoResp = new AdminInfoResp();
        adminInfoResp.setIcon(umsAdmin.getIcon());
        adminInfoResp.setUsername(umsAdmin.getUsername());

        List<UmsRole> umsRoleList = umsRoleDao.selectByAdminId(umsAdmin.getId());
        if (CollUtil.isEmpty(umsRoleList)) {
            throw new RuntimeException("角色列表为空");
        }

        List<UmsMenu> umsMenus = umsMenuDao
                .selectByRoleIds(umsRoleList.stream().map(UmsRole::getId).collect(Collectors.toList()));
        adminInfoResp.setMenus(umsMenus.stream().map(t -> {
            AdminMenusResp adminMenusResp = new AdminMenusResp();
            BeanUtil.copyProperties( t,adminMenusResp);
            return adminMenusResp;
        }).collect(Collectors.toList()));

        adminInfoResp.setRoles(umsRoleList.stream().map(UmsRole::getName).collect(Collectors.toList()));
        return adminInfoResp;
    }
}
