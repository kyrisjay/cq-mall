package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.OmsOrder;
import club.banyuan.mgt.dao.entity.OmsOrderExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OmsOrderDao {
    long countByExample(OmsOrderExample example);

    int deleteByExample(OmsOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OmsOrder record);

    int insertSelective(OmsOrder record);

    List<OmsOrder> selectByExample(OmsOrderExample example);

    OmsOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByExample(@Param("record") OmsOrder record, @Param("example") OmsOrderExample example);

    int updateByPrimaryKeySelective(OmsOrder record);

    int updateByPrimaryKey(OmsOrder record);

    int updateDeliveryById(String deliveryCompany, int i, String deliverySn, Date date, Long orderId);

    int updateNoteById( Long id, String note,Integer status);
}