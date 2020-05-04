package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsSkuStock;

import java.util.List;

public interface PmsSkuStockDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsSkuStock record);

    int insertSelective(PmsSkuStock record);

    PmsSkuStock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsSkuStock record);

    int updateByPrimaryKey(PmsSkuStock record);

    int insertMany(List<PmsSkuStock> pmsSkuStocks);

    int deleteByProductIds(List<Long> ids);

    List<PmsSkuStock> selectByProductId(Long productId);

    int deleteByProductId(Long productId);
}