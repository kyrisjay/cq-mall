package club.banyuan.mgt.service.impl;



import club.banyuan.mgt.dao.UmsResourceDao;
import club.banyuan.mgt.dao.entity.UmsResource;
import club.banyuan.mgt.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceDao umsResourceDao;

    @Override
    public List<UmsResource> getAllResource() {
        return umsResourceDao.selectAll();
    }

    @Override
    public List<UmsResource> getResourcesByAdminId(Long id) {
        return umsResourceDao.selectByAdminId(id);
    }
}
