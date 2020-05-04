package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.PmsBrand;
import club.banyuan.mgt.dto.PmsBrandResp;

import java.io.IOException;
import java.util.List;

public interface PmsBrandService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);

    Integer create(PmsBrand pmsBrand);

    PmsBrandResp pmsBrandInfo(Long brandId);

    Integer showStatus(List<Long> brandIds, Integer showStatus);

    Integer factoryStatus(List<Long> brandIds, Integer factoryStatus);

    Integer update(Long brandId, PmsBrand pmsBrand);

    Integer delete(Long brandId) throws IOException;
}
