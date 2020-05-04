package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.PmsProductAttributeCategoryTreeNode;

import java.util.List;

public interface PmsProductAttributeCategoryService {
    ResponsePages list(Integer pageNum, Integer pageSize);

    List<PmsProductAttributeCategoryTreeNode> listWithAttr();

    Integer create(String name);

    Integer update(String name, Long id);

    Integer delete(Long id);
}
