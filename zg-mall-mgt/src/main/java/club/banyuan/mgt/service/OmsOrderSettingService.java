package club.banyuan.mgt.service;

import club.banyuan.mgt.dao.entity.OmsOrderSetting;
import club.banyuan.mgt.dto.OmsOrderSettingResp;

public interface OmsOrderSettingService {
    OmsOrderSettingResp showOrderSetting(Long orderSettingId);

    Integer update(Long orderSettingId, OmsOrderSetting omsOrderSetting);
}
