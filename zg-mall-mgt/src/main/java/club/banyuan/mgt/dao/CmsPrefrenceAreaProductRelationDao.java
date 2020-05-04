package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.CmsPrefrenceAreaProductRelation;

import java.util.List;

public interface CmsPrefrenceAreaProductRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(CmsPrefrenceAreaProductRelation record);

    int insertSelective(CmsPrefrenceAreaProductRelation record);

    CmsPrefrenceAreaProductRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmsPrefrenceAreaProductRelation record);

    int updateByPrimaryKey(CmsPrefrenceAreaProductRelation record);

    int insertMany(List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelations);

    int deleteByProductIds(List<Long> ids);

    List<CmsPrefrenceAreaProductRelation> selectByProductId(Long productId);

    int deleteByProductId(Long productId);
}