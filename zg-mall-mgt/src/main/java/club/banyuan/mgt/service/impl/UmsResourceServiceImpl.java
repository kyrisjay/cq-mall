package club.banyuan.mgt.service.impl;


import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.UmsResourceDao;
import club.banyuan.mgt.dao.entity.UmsResource;
import club.banyuan.mgt.dao.entity.UmsResourceExample;
import club.banyuan.mgt.dto.UmsResourceResp;
import club.banyuan.mgt.service.UmsResourceService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.UMS_ADMIN_RESOURCE_NOT_EXIST;
import static club.banyuan.mgt.common.FailReason.UMS_RESOURCE_NAME_DUPLICATE;

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

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize,
                              String nameKeyword, String urlKeyword,
                              Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);

        UmsResourceExample umsResourceExample = new UmsResourceExample();
        UmsResourceExample.Criteria criteria = umsResourceExample.createCriteria();

        if (categoryId != null) {
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (StrUtil.isNotBlank(urlKeyword)) {
            criteria.andUrlLike(StrUtil.concat(true, "%", nameKeyword, "%"));
        }
        if (StrUtil.isNotBlank(nameKeyword)) {
            criteria.andNameLike(StrUtil.concat(true, "%", urlKeyword, "%"));
        }

        List<UmsResource> umsResources = umsResourceDao.selectByExample(umsResourceExample);
        List<UmsResourceResp> umsResourceRespList = umsResources.stream().map(t -> {
            UmsResourceResp umsResourceResp = new UmsResourceResp();
            BeanUtil.copyProperties(t, umsResourceResp);
            return umsResourceResp;
        }).collect(Collectors.toList());
        PageInfo<UmsResource> pageInfo = new PageInfo<>(umsResources);
        return ResponsePages.setPages(pageInfo, umsResourceRespList);
    }

    @Override
    public Integer update(@Valid UmsResource umsResource, Long resourceId) {
        UmsResource umsResource1 = umsResourceDao.selectByPrimaryKey(resourceId);
        if (ObjectUtil.isEmpty(umsResource1)) {
            throw new RequestFailException(UMS_ADMIN_RESOURCE_NOT_EXIST);
        }
        umsResource.setCategoryId(resourceId);

        return umsResourceDao.updateByPrimaryKey(umsResource);
    }

    @Override
    public Integer delete(Long resourceId) {
        UmsResource umsResource = umsResourceDao.selectByPrimaryKey(resourceId);
        if (ObjectUtil.isEmpty(umsResource)) {
            throw new RequestFailException(UMS_ADMIN_RESOURCE_NOT_EXIST);
        }

        return umsResourceDao.deleteByPrimaryKey(resourceId);
    }

    @Override
    public Integer create(UmsResource umsResource) {

        List<UmsResource> umsResources = umsResourceDao.selectAll();
        umsResources.forEach(t -> {
           if (t.getUrl().equals(umsResource.getUrl()) || t.getName().equals(umsResource.getName())){
                throw new RequestFailException(UMS_RESOURCE_NAME_DUPLICATE);
            }
        });

        umsResource.setCreateTime(new Date());
        return umsResourceDao.insert(umsResource);
    }
}
