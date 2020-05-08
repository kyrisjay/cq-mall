package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.OmsOrderReturnApplyResp;
import club.banyuan.mgt.dto.OmsUpdateApplyResp;

import java.util.Date;
import java.util.List;

public interface OmsOrderReturnApplyService {
    ResponsePages list(Integer pageNum, Integer pageSize, Long id, Integer status, Date createTime, String handleMan, Date handleTime);

    OmsOrderReturnApplyResp getInfo(Long id);


    Integer delete(List<Long> ids);

    Integer updateStatus(Long id, OmsUpdateApplyResp omsUpdateApplyResp);
}
