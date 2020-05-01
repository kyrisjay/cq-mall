package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mgt.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;

    @ResponseBody
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ResponseResult allList() {

        return ResponseResult.
                success(umsResourceCategoryService.allList());
    }

    @RequestMapping(value = "/update/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@RequestBody UmsResourceCategory umsResourceCategory,
                                 @PathVariable Long resourceId) {

        return ResponseResult
                .success(umsResourceCategoryService.update(umsResourceCategory, resourceId));
    }


    @RequestMapping(value = "/delete/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult delete(@PathVariable Long resourceId) {

        return ResponseResult
                .success(umsResourceCategoryService.delete(resourceId));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody UmsResourceCategory umsResourceCategory) {

        return ResponseResult
                .success(umsResourceCategoryService.create(umsResourceCategory));
    }

}
