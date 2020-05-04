package club.banyuan.mgt.service;

import club.banyuan.mgt.dto.PmsProductCategoryResp;

import java.util.List;

public interface PmsProductCategoryService {

    List<PmsProductCategoryResp> listWithChildren();
}
