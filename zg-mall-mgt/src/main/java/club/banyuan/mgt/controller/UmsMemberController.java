package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memberLevel")
public class UmsMemberController {

    @Autowired
    private UmsMemberService umsMemberService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(@RequestParam(value = "defaultStatus") Integer defaultStatus) {
        return ResponseResult.success(umsMemberService.list(defaultStatus));
    }
}
