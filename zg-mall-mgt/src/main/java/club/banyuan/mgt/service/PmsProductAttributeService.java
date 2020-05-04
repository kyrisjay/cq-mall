package club.banyuan.mgt.service;

import club.banyuan.mgt.dto.AttrInfoResp;

import java.util.List;

public interface PmsProductAttributeService {

    List<AttrInfoResp> attrInfo(Long productCategoryId);
}
