package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseResult list(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize){
        return ResponseResult.success(pmsProductAttributeCategoryService.list(pageNum,pageSize));
    }

    @ResponseBody
    @RequestMapping(value = "/list/withAttr",method = RequestMethod.GET)
    public ResponseResult listWithAttr(){
        return ResponseResult.success(pmsProductAttributeCategoryService.listWithAttr());
    }

    @ResponseBody
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseResult create(@RequestParam("name") String name){
        return ResponseResult.success(pmsProductAttributeCategoryService.create(name));
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseResult update(@RequestParam("name") String name,
                                 @PathVariable("id") Long id){
        return ResponseResult.success(pmsProductAttributeCategoryService.update(name,id));
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResponseResult delete(@PathVariable("id") Long id){
        return ResponseResult.success(pmsProductAttributeCategoryService.delete(id));
    }
}
