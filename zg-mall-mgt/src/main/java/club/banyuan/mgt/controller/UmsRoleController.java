package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dto.UmsRoleReq;
import club.banyuan.mgt.service.UmsRoleService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) String keyword) {
        return ResponseResult
                .success(umsRoleService.listByPages(pageNum, pageSize, keyword));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody @Valid UmsRoleReq umsRoleReq) {

        return ResponseResult
                .success(umsRoleService.create(umsRoleReq));
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody @Valid UmsRoleReq umsRoleReq) {

        return ResponseResult
                .success(umsRoleService.update(umsRoleReq));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestParam @NotNull Long ids) {

        return ResponseResult
                .success(umsRoleService.delete(ids));
    }

    @RequestMapping(value = "/listMenu/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult listMenu(@PathVariable @NotNull Long id) {

        return ResponseResult
                .success(umsRoleService.listMenu(id));
    }

    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult allocMenu(@RequestParam Long roleId,
                                    @RequestParam(value = "menuIds") @NotEmpty String menuIdStr) {
        List<String> stringList = Arrays.asList(menuIdStr.split(","));
        List<Long> menuIds = stringList.stream().map(Long::parseLong).collect(Collectors.toList());
        umsRoleService.allocMenu(roleId, menuIds);
        return ResponseResult
                .success(stringList.size());
    }

    @ResponseBody
    @RequestMapping(value = "/listAll",method = RequestMethod.GET)
    public ResponseResult allList(){
        return ResponseResult.success(umsRoleService.listAll());
    }


}
