package club.banyuan.mgt.dao;

import club.banyuan.mgt.dao.entity.PmsProductCategoryAttributeRelation;
import club.banyuan.mgt.dto.AttrInfoResp;

import java.util.List;

public interface PmsProductCategoryAttributeRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(PmsProductCategoryAttributeRelation record);

    int insertSelective(PmsProductCategoryAttributeRelation record);

    PmsProductCategoryAttributeRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsProductCategoryAttributeRelation record);

    int updateByPrimaryKey(PmsProductCategoryAttributeRelation record);

    int deleteByProductCategoryId(Long productCategoryId);

    int insertByProductAttributeIdList(List productAttributeIdList, Long productCategoryId);

    List<AttrInfoResp> selectByProductCategoryId(Long productCategoryId);
}