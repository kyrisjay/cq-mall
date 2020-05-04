package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.CmsSubjectProductRelation;

import java.util.List;

public interface CmsSubjectProductRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(CmsSubjectProductRelation record);

    int insertSelective(CmsSubjectProductRelation record);

    CmsSubjectProductRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CmsSubjectProductRelation record);

    int updateByPrimaryKey(CmsSubjectProductRelation record);

    int insertMany(List<CmsSubjectProductRelation> cmsSubjectProductRelations);

    int deleteByProductIds(List<Long> ids);

    List<CmsSubjectProductRelation> selectByProductId(Long productId);

    int deleteByProductId(Long productId);
}