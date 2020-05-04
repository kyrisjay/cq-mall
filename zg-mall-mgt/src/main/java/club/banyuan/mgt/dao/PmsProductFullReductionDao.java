package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsProductFullReduction;

import java.util.List;

public interface PmsProductFullReductionDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProductFullReduction record);

    int insertSelective(PmsProductFullReduction record);

    PmsProductFullReduction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductFullReduction record);

    int updateByPrimaryKey(PmsProductFullReduction record);

    int insertMany(List<PmsProductFullReduction> pmsProductFullReductions);

    int deleteByProductIds(List<Long> ids);

    List<PmsProductFullReduction> selectByProductId(Long productId);

    int deleteByProductId(Long productId);
}