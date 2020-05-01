package club.banyuan.mgt.service;

import club.banyuan.mgt.dao.entity.UmsResourceCategory;


import java.util.List;

public interface UmsResourceCategoryService {
    List<UmsResourceCategory> allList();

    Integer update(UmsResourceCategory umsResourceCategory, Long resourceId);

    Integer delete(Long resourceId);

    Integer create(UmsResourceCategory umsResourceCategory);
}
