package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.OmsOrderSetting;

public interface OmsOrderSettingDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsOrderSetting record);

    int insertSelective(OmsOrderSetting record);

    OmsOrderSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsOrderSetting record);

    int updateByPrimaryKey(OmsOrderSetting record);
}