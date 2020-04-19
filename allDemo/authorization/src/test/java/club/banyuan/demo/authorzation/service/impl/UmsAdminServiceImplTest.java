package club.banyuan.demo.authorzation.service.impl;

import static org.junit.Assert.*;

import club.banyuan.demo.authorization.dto.AdminLoginReq;
import club.banyuan.demo.authorization.dto.AdminLoginResp;
import club.banyuan.demo.authorization.service.AdminService;
import club.banyuan.demo.authorization.service.TokenService;
import cn.hutool.core.util.StrUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UmsAdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenService tokenService;

    @Test
    public void loginTest() {
        AdminLoginReq adminLoginReq = new AdminLoginReq();
        adminLoginReq.setPassword("banyuan");
        adminLoginReq.setUsername("admin");

        AdminLoginResp loginResp = adminService.login(adminLoginReq);
        String token = loginResp.getToken();
        assertTrue(StrUtil.isNotBlank(token));

        assertTrue(Long.parseLong(tokenService.parseSubject(token))> 0);
    }
}
