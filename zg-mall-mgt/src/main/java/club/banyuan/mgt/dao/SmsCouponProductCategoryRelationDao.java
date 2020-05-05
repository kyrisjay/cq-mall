package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.SmsCouponProductCategoryRelation;

import java.util.List;

public interface SmsCouponProductCategoryRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(SmsCouponProductCategoryRelation record);

    int insertSelective(SmsCouponProductCategoryRelation record);

    SmsCouponProductCategoryRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsCouponProductCategoryRelation record);

    int updateByPrimaryKey(SmsCouponProductCategoryRelation record);

    List<SmsCouponProductCategoryRelation> selectByCouponId(Long couponId);

    int deleteBycouponId(Long couponId);
}