package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.dao.UmsResourceCategoryDao;
import club.banyuan.mgt.dao.entity.UmsResourceCategory;
import club.banyuan.mgt.service.UmsResourceCategoryService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.UMS_RESOURCE_CATEGORY_NAME_DUPLICATE;
import static club.banyuan.mgt.common.FailReason.UMS_RESOURCE_CATEGORY_NOT_EXIST;

@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Autowired
    private UmsResourceCategoryDao umsResourceCategoryDao;

    @Override
    public List<UmsResourceCategory> allList() {

        List<UmsResourceCategory> umsResourceCategoryList = umsResourceCategoryDao.listAll();

        return umsResourceCategoryList.stream().map(t -> {
            UmsResourceCategory umsResourceCategory = new UmsResourceCategory();
            BeanUtil.copyProperties(t, umsResourceCategory);
            return umsResourceCategory;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer update(UmsResourceCategory umsResourceCategory, Long resourceId) {
        UmsResourceCategory umsResourceCategory1 = umsResourceCategoryDao.selectByPrimaryKey(resourceId);
        if (ObjectUtil.isEmpty(umsResourceCategory1)) {
            throw new RequestFailException(UMS_RESOURCE_CATEGORY_NOT_EXIST);
        }

        umsResourceCategory.setId(resourceId);
        return umsResourceCategoryDao.updateByPrimaryKey(umsResourceCategory);
    }

    @Override
    public Integer delete(Long resourceId) {
        UmsResourceCategory umsResourceCategory = umsResourceCategoryDao.selectByPrimaryKey(resourceId);

        if (ObjectUtil.isEmpty(umsResourceCategory)) {
            throw new RequestFailException(UMS_RESOURCE_CATEGORY_NOT_EXIST);
        }

        return umsResourceCategoryDao.deleteByPrimaryKey(resourceId);
    }

    @Override
    public Integer create(UmsResourceCategory umsResourceCategory) {
        List<UmsResourceCategory> umsResourceCategoryList = umsResourceCategoryDao.listAll();

        umsResourceCategoryList.forEach(t -> {
            if (t.getName().equals(umsResourceCategory.getName())) {
                throw new RequestFailException(UMS_RESOURCE_CATEGORY_NAME_DUPLICATE);
            }
        });

        umsResourceCategory.setCreateTime(new Date());
        return umsResourceCategoryDao.insert(umsResourceCategory);
    }
}
