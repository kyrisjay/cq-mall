package club.banyuan.mgt.service;


import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.UmsResource;

import javax.validation.Valid;
import java.util.List;

public interface UmsResourceService {

    List<UmsResource> getAllResource();
    List<UmsResource> getResourcesByAdminId(Long id);

    ResponsePages list(Integer pageNum, Integer pageSize, String nameKeyword, String urlKeyword, Long categoryId);

    Integer update(@Valid UmsResource umsResource, Long resourceId);

    Integer delete(Long resourceId);

    Integer create(UmsResource umsResource);
}