package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.SmsHomeAdvertise;
import club.banyuan.mgt.dto.SmsHomeAdvertiseResp;

import java.util.Date;
import java.util.List;

public interface SmsHomeAdvertiseService {
    ResponsePages list(Integer pageNum, Integer pageSize, String name, Integer type, Date endTime);

    Integer create(SmsHomeAdvertise smsHomeAdvertise);

    SmsHomeAdvertiseResp getInfo(Long id);

    Integer update(Long id, SmsHomeAdvertise smsHomeAdvertise);

    Integer delete(List<Long> ids);

    Integer updateStatus(Long id, Integer status);
}
