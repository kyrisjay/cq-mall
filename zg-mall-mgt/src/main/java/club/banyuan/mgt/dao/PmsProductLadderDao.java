package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsProductLadder;

import java.util.List;

public interface PmsProductLadderDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProductLadder record);

    int insertSelective(PmsProductLadder record);

    PmsProductLadder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductLadder record);

    int updateByPrimaryKey(PmsProductLadder record);

    int insertMany(List<PmsProductLadder> pmsProductLadders);

    int deleteByProductIds(List<Long> ids);

    List<PmsProductLadder> selectByProductId(Long productId);

    int deleteByProductId(Long productId);
}