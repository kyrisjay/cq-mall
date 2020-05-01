package club.banyuan.mgt.service.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import club.banyuan.mgt.dto.AdminInfoResp;
import club.banyuan.mgt.dto.AdminLoginReq;
import club.banyuan.mgt.dto.AdminLoginResp;
import club.banyuan.mgt.service.AdminService;
import club.banyuan.mgt.service.TokenService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.Assert;
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

        assertTrue(Long.parseLong(tokenService.parseSubject(token)) > 0);

    }

    @Test
    public void infoTest(){
        AdminInfoResp info = adminService.getInfo(3);
        Assert.assertTrue(CollUtil.isNotEmpty(info.getMenus()));
        Assert.assertTrue(CollUtil.isNotEmpty(info.getRoles()));
    }
}
