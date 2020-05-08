package club.banyuan.mgt.dao.entity;

import club.banyuan.mgt.dao.entity.OmsCompanyAddress;

import java.util.List;

public interface OmsCompanyAddressDao {
    int deleteByPrimaryKey(Long id);

    int insert(OmsCompanyAddress record);

    int insertSelective(OmsCompanyAddress record);

    OmsCompanyAddress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OmsCompanyAddress record);

    int updateByPrimaryKey(OmsCompanyAddress record);

    List<OmsCompanyAddress> selectAll();
}