package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsMemberPrice;

import java.util.List;

public interface PmsMemberPriceDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsMemberPrice record);

    int insertSelective(PmsMemberPrice record);

    PmsMemberPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsMemberPrice record);

    int updateByPrimaryKey(PmsMemberPrice record);

    int insertMany(List<PmsMemberPrice> pmsMemberPrices);

    int deleteByProductIds(List<Long> ids);

    List<PmsMemberPrice> selectByProductId(Long productId);

    int deleteByProductId(Long id);
}