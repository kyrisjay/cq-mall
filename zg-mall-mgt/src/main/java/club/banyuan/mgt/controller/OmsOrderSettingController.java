package club.banyuan.mgt.controller;

import club.banyuan.mgt.common.ResponseResult;
import club.banyuan.mgt.dao.entity.OmsOrderSetting;
import club.banyuan.mgt.service.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {
    @Autowired
    private OmsOrderSettingService orderSettingService;

    @RequestMapping(value = "/{orderSettingId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult showOrderSetting(@PathVariable("orderSettingId") Long orderSettingId) {
        return ResponseResult.success(orderSettingService.showOrderSetting(orderSettingId));
    }

    @RequestMapping(value = "/update/{orderSettingId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult update(@PathVariable("orderSettingId") Long orderSettingId,
                                 @RequestBody OmsOrderSetting omsOrderSetting) {
        return ResponseResult.success(orderSettingService.update(orderSettingId, omsOrderSetting));
    }
}
