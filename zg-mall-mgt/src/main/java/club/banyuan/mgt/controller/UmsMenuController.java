package club.banyuan.mgt.controller;


import club.banyuan.mgt.common.ResponseResult;

import club.banyuan.mgt.dao.entity.UmsMenu;
import club.banyuan.mgt.service.UmsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService umsMenuService;

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult treeList() {

        return ResponseResult
                .success(umsMenuService.treeList());
    }

    @RequestMapping(value = "/list/{menuParentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list(@PathVariable("menuParentId") Long menuParentId,
                               @RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize) {

        return ResponseResult
                .success(umsMenuService.list(pageNum, pageSize, menuParentId));
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getMenu(@PathVariable("menuId") Long menuId
    ) {

        return ResponseResult
                .success(umsMenuService.getMenu(menuId));
    }

    @RequestMapping(value = "update/{menuId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@PathVariable("menuId") Long menuId,
                                 @RequestBody UmsMenu umsMenu
    ) {

        return ResponseResult
                .success(umsMenuService.update(menuId, umsMenu));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody  @Valid UmsMenu umsMenu) {

        return ResponseResult
                .success(umsMenuService.create(umsMenu));
    }

    @RequestMapping(value = "delete/{menuId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@PathVariable("menuId") Long menuId) {

        return ResponseResult
                .success(umsMenuService.delete(menuId));
    }

}
