package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dao.entity.UmsAdmin;
import club.banyuan.mgt.dto.AdminLoginReq;
import club.banyuan.mgt.dto.AdminLoginResp;
import club.banyuan.mgt.service.AdminService;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestBody @Valid AdminLoginReq adminLoginReq) {
        AdminLoginResp adminLoginResp = adminService.login(adminLoginReq);
        return ResponseResult.success(adminLoginResp);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult info(Principal principal) {
        long adminId = Long.parseLong(principal.getName());

        return ResponseResult.success(adminService.getInfo(adminId));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam(required = false, value = "keyword") String keyword) {

        return ResponseResult
                .success(adminService.list(pageNum, pageSize, keyword));
    }

    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult role(@PathVariable Long adminId) {

        return ResponseResult
                .success(adminService.getRoleByAdminId(adminId));
    }

    @RequestMapping(value = "/update/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@PathVariable("adminId") Long adminId,
                                 @RequestBody @Valid UmsAdmin umsAdmin
    ) throws ParseException {
        return ResponseResult
                .success(adminService.update(umsAdmin, adminId));
    }

    @RequestMapping(value = "/delete/{adminId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@PathVariable("adminId") Long adminId
    ) {
        return ResponseResult
                .success(adminService.delete(adminId));
    }

    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateRole(@RequestParam("adminId") Long adminId,
                                     @RequestParam("roleIds") List<Long> roleIds){
        return ResponseResult.success(adminService.updateRole(adminId,roleIds));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult register(@RequestBody UmsAdmin admin){
        return ResponseResult.success(adminService.register(admin));
    }
}
