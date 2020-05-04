package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.PmsProductAttributeCategoryDao;
import club.banyuan.mgt.dao.PmsProductCategoryAttributeRelationDao;
import club.banyuan.mgt.dao.PmsProductCategoryDao;
import club.banyuan.mgt.dao.entity.PmsProductCategory;
import club.banyuan.mgt.dao.entity.PmsProductCategoryExample;
import club.banyuan.mgt.dto.PmsProductCategoryResp;
import club.banyuan.mgt.dto.PmsProductCategoryWithListResp;
import club.banyuan.mgt.service.PmsProductCategoryService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.PMS_PRODUCT_CATEGORY_NOT_EXIST;


@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {


    @Autowired
    private PmsProductCategoryDao pmsProductCategoryDao;

    @Autowired
    private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, Long parentId) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample pmsProductCategoryExample = new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria = pmsProductCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryDao.selectByExample(pmsProductCategoryExample);
        PageInfo<PmsProductCategory> pageInfo = new PageInfo<>(pmsProductCategories);
        List<PmsProductCategoryResp> collect = pmsProductCategories.stream().map(t -> {
            PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
            BeanUtil.copyProperties(t, pmsProductCategoryResp);
            return pmsProductCategoryResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public List<PmsProductCategoryResp> listWithChildren() {
        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryDao.selectAll();
        List<PmsProductCategoryResp> list = new ArrayList<>();
        pmsProductCategories.forEach(t -> {
            if (t.getParentId() == 0) {
                PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
                BeanUtil.copyProperties(t, pmsProductCategoryResp);
                pmsProductCategoryResp.setChildren(new ArrayList<>());
                list.add(pmsProductCategoryResp);
            }
        });

        list.forEach(parent -> {
            pmsProductCategories.forEach(child -> {
                if (parent.getId().equals(child.getParentId())) {
                    PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
                    BeanUtil.copyProperties(child, pmsProductCategoryResp);
                    parent.getChildren().add(pmsProductCategoryResp);
                }
            });
        });
        return list;
    }

    @Override
    public Integer create(PmsProductCategory pmsProductCategory) {
        return pmsProductCategoryDao.insert(pmsProductCategory);
    }

    @Override
    @Transactional
    public Integer update(PmsProductCategoryWithListResp pmsProductCategoryWithListResp, Long productCategoryId) {
        pmsProductCategoryDao.selectByPrimaryKey(productCategoryId);
        if (ObjectUtil.isEmpty(productCategoryId)) {
            throw new RequestFailException(PMS_PRODUCT_CATEGORY_NOT_EXIST);
        }
        pmsProductCategoryWithListResp.setId(productCategoryId);
        PmsProductCategory pmsProductCategory = pmsProductCategoryWithListResp.findPmsProductCategory();
        pmsProductCategoryDao.updateByPrimaryKey(pmsProductCategory);
        productCategoryAttributeRelationDao.deleteByProductCategoryId(productCategoryId);
        List productAttributeIdList = pmsProductCategoryWithListResp.getProductAttributeIdList();
        return productCategoryAttributeRelationDao.insertByProductAttributeIdList(productAttributeIdList, productCategoryId);
    }

    @Override
    public Integer delete(Long productCategoryId) {
        pmsProductCategoryDao.selectByPrimaryKey(productCategoryId);
        if (ObjectUtil.isEmpty(productCategoryId)) {
            throw new RequestFailException(PMS_PRODUCT_CATEGORY_NOT_EXIST);
        }
        return pmsProductCategoryDao.deleteByPrimaryKey(productCategoryId);
    }

    @Override
    public PmsProductCategoryResp showInfo(Long productCategoryId) {
        PmsProductCategory pmsProductCategory = pmsProductCategoryDao.selectByPrimaryKey(productCategoryId);
        if (ObjectUtil.isEmpty(pmsProductCategory)) {
            throw new RequestFailException(PMS_PRODUCT_CATEGORY_NOT_EXIST);
        }
        PmsProductCategoryResp pmsProductCategoryResp = new PmsProductCategoryResp();
        BeanUtil.copyProperties(pmsProductCategory, pmsProductCategoryResp);
        return pmsProductCategoryResp;
    }

    @Override
    public Integer updateNavStatus(Long ids, Integer navStatus) {
        pmsProductCategoryDao.selectByPrimaryKey(ids);
        if (ObjectUtil.isEmpty(ids)){
            throw new RequestFailException(PMS_PRODUCT_CATEGORY_NOT_EXIST);
        }
        return pmsProductCategoryDao.updateNavStatusById(ids,navStatus);
    }

    @Override
    public Integer updateShowStatus(Long ids, Integer showStatus) {
        pmsProductCategoryDao.selectByPrimaryKey(ids);
        if (ObjectUtil.isEmpty(ids)){
            throw new RequestFailException(PMS_PRODUCT_CATEGORY_NOT_EXIST);
        }
        return pmsProductCategoryDao.updateShowStatusById(ids,showStatus);
    }
}
