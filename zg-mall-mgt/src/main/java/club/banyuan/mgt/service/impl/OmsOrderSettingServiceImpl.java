package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.dao.OmsOrderSettingDao;
import club.banyuan.mgt.dao.entity.OmsOrderSetting;
import club.banyuan.mgt.dto.OmsOrderSettingResp;
import club.banyuan.mgt.service.OmsOrderSettingService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static club.banyuan.mgt.common.FailReason.OMS_ORDER_SETTING_NOT_EXIST;

@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {

    @Autowired
    private OmsOrderSettingDao omsOrderSettingDao;

    @Override
    public OmsOrderSettingResp showOrderSetting(Long orderSettingId) {
        OmsOrderSetting omsOrderSetting = omsOrderSettingDao.selectByPrimaryKey(orderSettingId);
        OmsOrderSettingResp omsOrderSettingResp = new OmsOrderSettingResp();
        BeanUtil.copyProperties(omsOrderSetting, omsOrderSettingResp);
        return omsOrderSettingResp;
    }

    @Override
    public Integer update(Long orderSettingId, OmsOrderSetting omsOrderSetting) {
        OmsOrderSetting omsOrderSetting1 = omsOrderSettingDao.selectByPrimaryKey(orderSettingId);
        if (BeanUtil.isEmpty(omsOrderSetting1)) {
            throw new RequestFailException(OMS_ORDER_SETTING_NOT_EXIST);
        }
        omsOrderSetting.setId(orderSettingId);
        return omsOrderSettingDao.updateByPrimaryKey(omsOrderSetting);
    }
}
