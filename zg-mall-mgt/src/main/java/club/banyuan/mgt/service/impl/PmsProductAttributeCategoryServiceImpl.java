package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.PmsProductAttributeCategoryDao;
import club.banyuan.mgt.dao.PmsProductAttributeDao;
import club.banyuan.mgt.dao.entity.PmsProductAttribute;
import club.banyuan.mgt.dao.entity.PmsProductAttributeCategory;
import club.banyuan.mgt.dto.PmsProductAttributeCategoryResp;
import club.banyuan.mgt.dto.PmsProductAttributeCategoryTreeNode;
import club.banyuan.mgt.service.PmsProductAttributeCategoryService;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.PMS_PRODUCT_ATTRIBUTE_CATEGORY_NAME_DUPLICATE;
import static club.banyuan.mgt.common.FailReason.PMS_PRODUCT_ATTRIBUTE_CATEGORY_NOT_EXIST;

@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {

    @Autowired
    private PmsProductAttributeDao pmsProductAttributeDao;

    @Autowired
    private PmsProductAttributeCategoryDao pmsProductAttributeCategoryDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = pmsProductAttributeCategoryDao.selectAll();
        PageInfo<PmsProductAttributeCategory> pageInfo = new PageInfo<>(pmsProductAttributeCategories);
        List<PmsProductAttributeCategoryResp> collect = pmsProductAttributeCategories.stream().map(t -> {
            PmsProductAttributeCategoryResp pmsProductAttributeCategoryResp = new PmsProductAttributeCategoryResp();
            BeanUtil.copyProperties(t, pmsProductAttributeCategoryResp);
            return pmsProductAttributeCategoryResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public List<PmsProductAttributeCategoryTreeNode> listWithAttr() {
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = pmsProductAttributeCategoryDao.selectAll();
        List<PmsProductAttributeCategoryTreeNode> treeNodes = new ArrayList<>();
        pmsProductAttributeCategories.forEach(t -> {
            List<PmsProductAttribute> pmsProductAttributes =
                    pmsProductAttributeDao.selectByProductAttributeCategoryId(t.getId());
            PmsProductAttributeCategoryTreeNode pmsProductAttributeCategoryTreeNode = new PmsProductAttributeCategoryTreeNode();
            BeanUtil.copyProperties(t, pmsProductAttributeCategoryTreeNode);
            pmsProductAttributeCategoryTreeNode.setProductAttributeList(pmsProductAttributes);
            treeNodes.add(pmsProductAttributeCategoryTreeNode);
        });

        return treeNodes;
    }

    @Override
    public Integer create(String name) {
        List<PmsProductAttributeCategory> pmsProductAttributeCategories = pmsProductAttributeCategoryDao.selectAll();
        pmsProductAttributeCategories.forEach(t->{
            if (t.getName().equals(name)){
                throw new RequestFailException(PMS_PRODUCT_ATTRIBUTE_CATEGORY_NAME_DUPLICATE);
            }
        });
        PmsProductAttributeCategory pmsProductAttributeCategory = new PmsProductAttributeCategory();
        pmsProductAttributeCategory.setName(name);
        return pmsProductAttributeCategoryDao.insert(pmsProductAttributeCategory);
    }

    @Override
    public Integer update(String name, Long id) {
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(pmsProductAttributeCategory)){
            throw new RequestFailException(PMS_PRODUCT_ATTRIBUTE_CATEGORY_NOT_EXIST);
        }
        pmsProductAttributeCategory.setName(name);
        return pmsProductAttributeCategoryDao.updateByPrimaryKey(pmsProductAttributeCategory);
    }

    @Override
    public Integer delete(Long id) {
        PmsProductAttributeCategory pmsProductAttributeCategory = pmsProductAttributeCategoryDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(pmsProductAttributeCategory)){
            throw new RequestFailException(PMS_PRODUCT_ATTRIBUTE_CATEGORY_NOT_EXIST);
        }
        return pmsProductAttributeCategoryDao.deleteByPrimaryKey(id);
    }
}
