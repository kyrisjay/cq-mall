package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.CreateProductReq;
import club.banyuan.mgt.dto.PmsProductInfoResp;

import java.io.IOException;
import java.util.List;

public interface PmsProductService {

    ResponsePages list(Integer pageNum, Integer pageSize, String keyword, Integer publishStatus, Integer verifyStatus, String productSn, Long productCategoryId, Long brandId);

    Integer create(CreateProductReq createProductReq);

    Integer delete(List<Long> ids, Integer deleteStatus);

    PmsProductInfoResp updateInfo(Long productId);

    Integer update(CreateProductReq createProductReq, Long productId) throws IOException;

    Integer publishStatus(List<Long> ids, Integer publishStatus);

    Integer recommendStatus(List<Long> ids, Integer recommendStatus);

    Integer newStatus(List<Long> ids, Integer newStatus);
}
