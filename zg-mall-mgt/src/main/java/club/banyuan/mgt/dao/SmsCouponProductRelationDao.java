package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.SmsCouponProductRelation;

import java.util.List;

public interface SmsCouponProductRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(SmsCouponProductRelation record);

    int insertSelective(SmsCouponProductRelation record);

    SmsCouponProductRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsCouponProductRelation record);

    int updateByPrimaryKey(SmsCouponProductRelation record);

    List<SmsCouponProductRelation> selectByCouponId(Long couponId);

    int deleteByCouponId(Long couponId);
}