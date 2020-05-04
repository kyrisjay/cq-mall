package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.SmsFlashPromotionProductRelation;

import java.util.List;

public interface SmsFlashPromotionProductRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(SmsFlashPromotionProductRelation record);

    int insertSelective(SmsFlashPromotionProductRelation record);

    SmsFlashPromotionProductRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsFlashPromotionProductRelation record);

    int updateByPrimaryKey(SmsFlashPromotionProductRelation record);

    int deleteByProductIds(List<Long> ids);

    List<SmsFlashPromotionProductRelation> selectByProductId(Long productId);
}