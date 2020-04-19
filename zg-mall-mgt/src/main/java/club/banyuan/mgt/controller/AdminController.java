package club.banyuan.mgt.controller;



import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dto.AdminLoginReq;
import club.banyuan.mgt.dto.AdminLoginResp;
import club.banyuan.mgt.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

