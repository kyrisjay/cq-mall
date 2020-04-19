package club.banyuan.mgt.service;


import club.banyuan.mgt.dao.entity.UmsResource;

import java.util.List;

public interface UmsResourceService {

    List<UmsResource> getAllResource();
    List<UmsResource> getResourcesByAdminId(Long id);
}