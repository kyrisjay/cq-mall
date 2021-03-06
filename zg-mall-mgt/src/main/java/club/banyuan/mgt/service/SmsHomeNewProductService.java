package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.SmsHomeNewProduct;

import java.util.List;

public interface SmsHomeNewProductService {
    ResponsePages list(Integer pageNum, Integer pageSize, String productName, Integer recommendStatus);

    Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    Integer create(List<SmsHomeNewProduct> smsHomeNewProducts);

    Integer updateSort(Integer sort, Long id);

    Integer delete(List<Long> ids);
}
