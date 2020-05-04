package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;

public interface PmsProductAttributeCategoryService {
    ResponsePages list(Integer pageNum, Integer pageSize);
}
