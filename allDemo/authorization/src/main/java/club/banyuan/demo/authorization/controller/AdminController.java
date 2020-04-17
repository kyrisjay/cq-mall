package club.banyuan.demo.authorization.controller;

import club.banyuan.demo.authorization.common.ResponseResult;
import club.banyuan.demo.authorization.dto.AdminLoginReq;
import club.banyuan.demo.authorization.dto.AdminLoginResp;
import club.banyuan.demo.authorization.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestBody AdminLoginReq adminLoginReq) {
        AdminLoginResp adminLoginResp = adminService.login(adminLoginReq);
        return ResponseResult.success(adminLoginResp);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult auth() {
        return ResponseResult.success("success");
    }
}
