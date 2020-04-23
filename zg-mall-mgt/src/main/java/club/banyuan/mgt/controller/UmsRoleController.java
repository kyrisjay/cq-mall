package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dto.UmsRoleRep;
import club.banyuan.mgt.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody @Valid UmsRoleRep umsRoleRep) {

        return ResponseResult
                .success(umsRoleService.create(umsRoleRep));
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody @Valid UmsRoleRep umsRoleRep, @PathVariable Long id) {

        return ResponseResult
                .success(umsRoleService.update(umsRoleRep, id));
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

}
