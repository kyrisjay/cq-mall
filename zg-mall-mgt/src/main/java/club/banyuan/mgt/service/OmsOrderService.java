package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.DeliveryResp;
import club.banyuan.mgt.dto.OmsOrderInfoResp;

import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface OmsOrderService {
    ResponsePages list(Integer pageNum, Integer pageSize, String orderSn, String receiverKeyword, Integer status, Integer orderType, Integer sourceType, Date createTime);

    OmsOrderInfoResp showOrderInfo(Long orderId);

    Integer delivery(List<DeliveryResp> deliveryResp, Principal principal);

    Integer delete(List<Long> ids);

    Integer close(List<Long> ids, String note, Principal principal);

    Integer updateNote(Long id, String note, Integer status);
}
