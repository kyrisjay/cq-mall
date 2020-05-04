package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;

public interface PmsBrandService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);
}
