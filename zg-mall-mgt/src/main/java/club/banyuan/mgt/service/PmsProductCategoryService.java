package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.PmsProductCategory;
import club.banyuan.mgt.dto.PmsProductCategoryResp;
import club.banyuan.mgt.dto.PmsProductCategoryWithListResp;

import java.util.List;

public interface PmsProductCategoryService {
    ResponsePages list(Integer pageNum, Integer pageSize, Long parentId);

    List<PmsProductCategoryResp> listWithChildren();

    Integer create(PmsProductCategory pmsProductCategory);

    Integer update(PmsProductCategoryWithListResp pmsProductCategory, Long productCategoryId);

    Integer delete(Long productCategoryId);

    PmsProductCategoryResp showInfo(Long productCategoryId);

    Integer updateNavStatus(Long ids, Integer navStatus);

    Integer updateShowStatus(Long ids, Integer showStatus);
}
