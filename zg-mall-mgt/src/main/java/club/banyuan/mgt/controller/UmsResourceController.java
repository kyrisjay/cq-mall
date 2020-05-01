package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dao.entity.UmsResource;
import club.banyuan.mgt.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService umsResourceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String nameKeyword,
            @RequestParam(required = false) String urlKeyword,
            @RequestParam(required = false) Long categoryId) {
        return ResponseResult
                .success(umsResourceService.list(pageNum, pageSize, nameKeyword, urlKeyword, categoryId));
    }


    @RequestMapping(value = "/update/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody @Valid UmsResource umsResource,
                                 @PathVariable Long resourceId) {

        return ResponseResult
                .success(umsResourceService.update(umsResource, resourceId));
    }

    @RequestMapping(value = "/delete/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@PathVariable Long resourceId) {

        return ResponseResult
                .success(umsResourceService.delete(resourceId));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody @Valid UmsResource umsResource) {

        return ResponseResult
                .success(umsResourceService.create(umsResource));
    }

}
