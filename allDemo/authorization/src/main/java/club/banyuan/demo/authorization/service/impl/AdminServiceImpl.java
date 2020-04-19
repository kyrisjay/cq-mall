package club.banyuan.demo.authorization.service.impl;

import club.banyuan.demo.authorization.dao.UmsAdminDao;
import club.banyuan.demo.authorization.dao.entity.UmsAdmin;
import club.banyuan.demo.authorization.dao.entity.UmsAdminExample;
import club.banyuan.demo.authorization.dao.entity.UmsResource;
import club.banyuan.demo.authorization.dto.AdminLoginReq;
import club.banyuan.demo.authorization.dto.AdminLoginResp;
import club.banyuan.demo.authorization.security.AdminUserDetails;
import club.banyuan.demo.authorization.security.ResourceConfigAttribute;
import club.banyuan.demo.authorization.service.AdminService;
import club.banyuan.demo.authorization.service.TokenService;
import club.banyuan.demo.authorization.service.UmsResourceService;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final String SCHEMA = "Bearer";
    private static final String TOKEN_HEAD_KEY = "Authorization";

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UmsAdminDao umsAdminDao;

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
}
