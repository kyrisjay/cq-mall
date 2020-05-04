package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.dao.PmsProductCategoryAttributeRelationDao;
import club.banyuan.mgt.dto.AttrInfoResp;
import club.banyuan.mgt.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {

    @Autowired
    private PmsProductCategoryAttributeRelationDao pmsProductCategoryAttributeRelationDao;

    @Override
    public List<AttrInfoResp> attrInfo(Long productCategoryId) {
        return  pmsProductCategoryAttributeRelationDao.selectByProductCategoryId(productCategoryId);
    }
}
