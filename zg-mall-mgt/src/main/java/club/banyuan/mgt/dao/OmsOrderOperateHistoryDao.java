package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.OmsOrderOperateHistory;

import java.util.Date;
import java.util.List;

public interface OmsOrderOperateHistoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsOrderOperateHistory record);

    int insertSelective(OmsOrderOperateHistory record);

    OmsOrderOperateHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrderOperateHistory record);

    int updateByPrimaryKey(OmsOrderOperateHistory record);

    List<OmsOrderOperateHistory> selectByOrderId(Long orderId);

    int insertByOrderId(Long orderId, String nickName, Date date, int i, String 完成发货);

    Integer deleteByOrderIds(List<Long> ids);
}