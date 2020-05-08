package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.OmsOrderReturnReason;
import club.banyuan.mgt.dto.OmsOrderReturnReasonResp;

import java.util.List;

public interface OmsOrderReturnReasonService {
    ResponsePages list(Integer pageNum, Integer pageSize);

    Integer create(OmsOrderReturnReason omsOrderReturnReason);

    OmsOrderReturnReasonResp getInfo(Long id);

    Integer update(OmsOrderReturnReason omsOrderReturnReason, Long id);

    Integer delete(List<Long> ids);

    Integer updateStatus(List<Long> ids, Integer status);
}
