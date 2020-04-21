package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseResult.success(umsRoleService.listByPages(pageNum, pageSize, keyword));
    }
}
