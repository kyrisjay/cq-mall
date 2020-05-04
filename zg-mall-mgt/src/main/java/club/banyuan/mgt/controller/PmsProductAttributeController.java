package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {

    @Autowired
    private PmsProductAttributeService productAttributeService;

    @ResponseBody
    @RequestMapping(value = "/attrInfo/{productCategoryId}",method = RequestMethod.GET)
    public ResponseResult attrInfo(@PathVariable("productCategoryId") Long productCategoryId){
        return ResponseResult.success(productAttributeService.attrInfo(productCategoryId));
    }
}
