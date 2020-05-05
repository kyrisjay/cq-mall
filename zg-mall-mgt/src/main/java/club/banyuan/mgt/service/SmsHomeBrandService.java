package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.SmsHomeBrand;

import java.util.List;

public interface SmsHomeBrandService {
    ResponsePages list(Integer pageNum, Integer pageSize, String brandName, Integer recommendStatus);

    Integer create(List<SmsHomeBrand> smsHomeBrands);

    Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Integer updateSort(Integer sort, Long id);

    Integer delete(List<Long> ids);
}
