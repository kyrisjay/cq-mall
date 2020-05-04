package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.*;
import club.banyuan.mgt.dao.entity.*;
import club.banyuan.mgt.dto.CreateProductReq;
import club.banyuan.mgt.dto.PmsProduceResp;
import club.banyuan.mgt.dto.PmsProductInfoResp;
import club.banyuan.mgt.service.OssFileService;
import club.banyuan.mgt.service.PmsProductService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.PMS_PRODUCT_NOT_EXIST;

@Service
public class PmsProductServiceImpl implements PmsProductService {

    @Autowired
    private PmsProductDao pmsProductDao;

    @Autowired
    private SmsFlashPromotionProductRelationDao smsFlashPromotionProductRelationDao;

    @Autowired
    private PmsMemberPriceDao pmsMemberPriceDao;

    @Autowired
    private PmsProductFullReductionDao pmsProductFullReductionDao;

    @Autowired
    private PmsProductLadderDao pmsProductLadderDao;

    @Autowired
    private PmsProductAttributeValueDao pmsProductAttributeValueDao;

    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;

    @Autowired
    private CmsSubjectProductRelationDao cmsSubjectProductRelationDao;

    @Autowired
    private CmsPrefrenceAreaProductRelationDao cmsPrefrenceAreaProductRelationDao;

    @Autowired
    private OssFileService ossFileService;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize,
                              String keyword, Integer publishStatus,
                              Integer verifyStatus, String productSn,
                              Long productCategoryId, Long brandId) {

        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample pmsProductExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = pmsProductExample.createCriteria();

        if (StrUtil.isNotBlank(keyword)) {
            criteria.andNameLike(StrUtil.concat(true, "%", keyword, "%"));
        }
        if (StrUtil.isNotBlank(productSn)) {
            criteria.andProductSnEqualTo(StrUtil.concat(true, "%", productSn, "%"));
        }
        if (publishStatus != null) {
            criteria.andPublishStatusEqualTo(publishStatus);
        }
        if (verifyStatus != null) {
            criteria.andVerifyStatusEqualTo(verifyStatus);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        List<PmsProduct> pmsProducts = pmsProductDao.selectByExample(pmsProductExample);
        PageInfo<PmsProduct> pageInfo = new PageInfo<>(pmsProducts);
        List<PmsProduceResp> pmsProduceRespList = pmsProducts.stream().map(t -> {
            PmsProduceResp pmsProduceResp = new PmsProduceResp();
            BeanUtil.copyProperties(t, pmsProduceResp);
            return pmsProduceResp;
        }).collect(Collectors.toList());

        return ResponsePages.setPages(pageInfo, pmsProduceRespList);
    }

    @Override
    @Transactional
    public Integer create(CreateProductReq createProductReq) {
        PmsProduct pmsProduct = createProductReq.findPmsProduct();
        SmsFlashPromotionProductRelation smsFlashPromotionProductRelation = createProductReq.findSmsFlashPromotionProductRelation();

        List<PmsMemberPrice> pmsMemberPrices = createProductReq.getMemberPriceList();
        List<PmsProductFullReduction> pmsProductFullReductions = createProductReq.getProductFullReductionList();
        List<PmsProductLadder> pmsProductLadders = createProductReq.getProductLadderList();
        List<PmsProductAttributeValue> pmsProductAttributeValues = createProductReq.getProductAttributeValueList();
        List<PmsSkuStock> pmsSkuStocks = createProductReq.getSkuStockList();
        List<CmsSubjectProductRelation> cmsSubjectProductRelations = createProductReq.getSubjectProductRelationList();
        List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelations = createProductReq.getPrefrenceAreaProductRelationList();
        pmsProductDao.insert(pmsProduct);
        Long productId = pmsProduct.getId();
        smsFlashPromotionProductRelation.setProductId(productId);
        smsFlashPromotionProductRelationDao.insert(smsFlashPromotionProductRelation);

        if (pmsMemberPrices.size() > 0) {
            pmsMemberPrices.forEach(t -> {
                t.setProductId(productId);
            });
            pmsMemberPriceDao.insertMany(pmsMemberPrices);
        }
        if (pmsProductFullReductions.size() > 0) {
            pmsProductFullReductions.forEach(t -> {
                t.setProductId(productId);
            });
            pmsProductFullReductionDao.insertMany(pmsProductFullReductions);
        }

        if (pmsProductLadders.size() > 0) {
            pmsProductLadders.forEach(t -> {
                t.setProductId(productId);
            });
            pmsProductLadderDao.insertMany(pmsProductLadders);
        }

        if (pmsProductAttributeValues.size() > 0) {
            pmsProductAttributeValues.forEach(t -> {
                t.setProductId(productId);
            });
            pmsProductAttributeValueDao.insertMany(pmsProductAttributeValues);
        }
        if (pmsSkuStocks.size() > 0) {
            pmsSkuStocks.forEach(t -> {
                t.setProductId(productId);
            });
            pmsSkuStockDao.insertMany(pmsSkuStocks);
        }

        if (cmsPrefrenceAreaProductRelations.size() > 0) {
            cmsPrefrenceAreaProductRelations.forEach(t -> {
                t.setProductId(productId);
            });
            cmsSubjectProductRelationDao.insertMany(cmsSubjectProductRelations);
        }

        if (cmsPrefrenceAreaProductRelations.size() > 0) {
            cmsPrefrenceAreaProductRelations.forEach(t -> {
                t.setProductId(productId);
            });
            cmsPrefrenceAreaProductRelationDao.insertMany(cmsPrefrenceAreaProductRelations);
        }
        return 1;
    }

    @Override
    public Integer delete(List<Long> ids, Integer deleteStatus) {
        List<PmsProduct> pmsProducts = pmsProductDao.selectByPrimaryKeys(ids);
        pmsProducts.forEach(t -> {
            if (ObjectUtil.isEmpty(t)) {
                throw new RequestFailException(PMS_PRODUCT_NOT_EXIST);
            }
        });

        pmsProductDao.deleteByPrimaryKeys(ids);
        smsFlashPromotionProductRelationDao.deleteByProductIds(ids);
        pmsMemberPriceDao.deleteByProductIds(ids);
        pmsProductFullReductionDao.deleteByProductIds(ids);
        pmsProductLadderDao.deleteByProductIds(ids);
        pmsProductAttributeValueDao.deleteByProductIds(ids);
        pmsSkuStockDao.deleteByProductIds(ids);
        cmsSubjectProductRelationDao.deleteByProductIds(ids);
        cmsPrefrenceAreaProductRelationDao.deleteByProductIds(ids);

        pmsProducts.forEach(t -> {
            String pic = t.getPic();
            if (StrUtil.isNotBlank(pic)) {
                try {
                    ossFileService.delete(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String albumPics = t.getAlbumPics();
            if (StrUtil.isNotBlank(albumPics)) {
                String[] split = albumPics.split(",");
                Arrays.stream(split).forEach(s -> {
                    try {
                        ossFileService.delete(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        return deleteStatus;
    }

    @Override
    public PmsProductInfoResp updateInfo(Long productId) {
        PmsProduct pmsProduct = pmsProductDao.selectByPrimaryKey(productId);
        if (ObjectUtil.isEmpty(pmsProduct)) {
            throw new RequestFailException(PMS_PRODUCT_NOT_EXIST);
        }

        List<SmsFlashPromotionProductRelation> smsFlashPromotionProductRelations = smsFlashPromotionProductRelationDao.selectByProductId(productId);
        List<PmsMemberPrice> pmsMemberPrices = pmsMemberPriceDao.selectByProductId(productId);
        List<PmsProductFullReduction> pmsProductFullReductions = pmsProductFullReductionDao.selectByProductId(productId);
        List<PmsProductLadder> pmsProductLadders = pmsProductLadderDao.selectByProductId(productId);
        List<PmsProductAttributeValue> pmsProductAttributeValues = pmsProductAttributeValueDao.selectByProductId(productId);
        List<PmsSkuStock> pmsSkuStocks = pmsSkuStockDao.selectByProductId(productId);
        List<CmsSubjectProductRelation> cmsSubjectProductRelations = cmsSubjectProductRelationDao.selectByProductId(productId);
        List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelations = cmsPrefrenceAreaProductRelationDao.selectByProductId(productId);
        PmsProductInfoResp pmsProductInfoResp = new PmsProductInfoResp();
        pmsProductInfoResp.setPmsProduct(pmsProduct);
        if(CollUtil.isNotEmpty(smsFlashPromotionProductRelations)) {
            pmsProductInfoResp.setSmsFlashPromotionProductRelation(smsFlashPromotionProductRelations.get(0));
        }
        if(CollUtil.isNotEmpty(pmsMemberPrices)) {
            pmsProductInfoResp.setMemberPriceList(pmsMemberPrices);
        }
        if(CollUtil.isNotEmpty(pmsProductFullReductions)) {
            pmsProductInfoResp.setProductFullReductionList(pmsProductFullReductions);
        }
        if(CollUtil.isNotEmpty(pmsProductLadders)) {
            pmsProductInfoResp.setProductLadderList(pmsProductLadders);
        }
        if(CollUtil.isNotEmpty(pmsProductAttributeValues)) {
            pmsProductInfoResp.setProductAttributeValueList(pmsProductAttributeValues);
        }
        if(CollUtil.isNotEmpty(pmsSkuStocks)) {
            pmsProductInfoResp.setSkuStockList(pmsSkuStocks);
        }
        if(CollUtil.isNotEmpty(cmsSubjectProductRelations)) {
            pmsProductInfoResp.setSubjectProductRelationList(cmsSubjectProductRelations);
        }
        if(CollUtil.isNotEmpty(cmsPrefrenceAreaProductRelations)) {
            pmsProductInfoResp.setPrefrenceAreaProductRelationList(cmsPrefrenceAreaProductRelations);
        }
        return pmsProductInfoResp;
    }

    @Override
    @Transactional
    public Integer update(CreateProductReq createProductReq, Long productId) {
        PmsProduct pmsProduct = pmsProductDao.selectByPrimaryKey(productId);
        if (ObjectUtil.isEmpty(pmsProduct)) {
            throw new RequestFailException(PMS_PRODUCT_NOT_EXIST);
        }
        SmsFlashPromotionProductRelation smsFlashPromotionProductRelation = createProductReq.findSmsFlashPromotionProductRelation();
        List<PmsMemberPrice> pmsMemberPrices = createProductReq.getMemberPriceList();
        List<PmsProductFullReduction> pmsProductFullReductions = createProductReq.getProductFullReductionList();
        List<PmsProductLadder> pmsProductLadders = createProductReq.getProductLadderList();
        List<PmsProductAttributeValue> pmsProductAttributeValues = createProductReq.getProductAttributeValueList();
        List<PmsSkuStock> pmsSkuStocks = createProductReq.getSkuStockList();
        List<CmsSubjectProductRelation> cmsSubjectProductRelations = createProductReq.getSubjectProductRelationList();
        List<CmsPrefrenceAreaProductRelation> cmsPrefrenceAreaProductRelations = createProductReq.getPrefrenceAreaProductRelationList();

        PmsProduct pmsProduct1 = createProductReq.findPmsProduct();
        pmsProduct1.setId(productId);
        pmsProductDao.updateByPrimaryKey(pmsProduct1);

        smsFlashPromotionProductRelationDao.deleteByPrimaryKey(productId);
        smsFlashPromotionProductRelation.setProductId(productId);
        smsFlashPromotionProductRelationDao.insert(smsFlashPromotionProductRelation);

        pmsMemberPriceDao.deleteByProductId(productId);
        if (pmsMemberPrices.size() > 0) {
            pmsMemberPrices.forEach(t -> {
                t.setProductId(productId);
            });
            pmsMemberPriceDao.insertMany(pmsMemberPrices);
        }

        pmsProductFullReductionDao.deleteByProductId(productId);
        if (pmsProductFullReductions.size() > 0) {
            pmsProductFullReductions.forEach(t -> {
                t.setId(productId);
            });
            pmsProductFullReductionDao.insertMany(pmsProductFullReductions);
        }

        pmsProductLadderDao.deleteByProductId(productId);
        if (pmsProductLadders.size() > 0) {
            pmsProductLadders.forEach(t -> {
                t.setProductId(productId);
            });
            pmsProductLadderDao.insertMany(pmsProductLadders);
        }

        pmsProductAttributeValueDao.deleteByProductId(productId);
        if (pmsProductAttributeValues.size() > 0) {
            pmsProductAttributeValues.forEach(t -> {
                t.setProductId(productId);
            });
            pmsProductAttributeValueDao.insertMany(pmsProductAttributeValues);
        }

        pmsSkuStockDao.deleteByProductId(productId);
        if (pmsSkuStocks.size() > 0) {
            pmsSkuStocks.forEach(t -> {
                t.setProductId(productId);
            });
            pmsSkuStockDao.insertMany(pmsSkuStocks);
        }

        int i = cmsSubjectProductRelationDao.deleteByProductId(productId);
        if (cmsSubjectProductRelations.size() > 0) {
            cmsSubjectProductRelations.forEach(t -> {
                t.setProductId(productId);
            });
            cmsSubjectProductRelationDao.insertMany(cmsSubjectProductRelations);
        }

        cmsPrefrenceAreaProductRelationDao.deleteByProductId(productId);
        if (cmsPrefrenceAreaProductRelations.size() > 0) {
            cmsPrefrenceAreaProductRelations.forEach(t -> {
                t.setProductId(productId);
            });
            cmsPrefrenceAreaProductRelationDao.insertMany(cmsPrefrenceAreaProductRelations);
        }

        String pic1 = pmsProduct1.getPic();
        String pic = pmsProduct.getPic();

        if (StrUtil.isNotBlank(pic)) {
            if (!pic.equals(pic1)) {
                try {
                    ossFileService.delete(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String albumPics = pmsProduct.getAlbumPics();
        String albumPics1 = pmsProduct1.getAlbumPics();
        if (StrUtil.isNotBlank(albumPics) && StrUtil.isNotBlank(albumPics1)) {
            String[] split = albumPics.split(",");
            String[] split1 = albumPics1.split(",");
            Arrays.stream(split).forEach(t -> {
                List<String> collect = Arrays.stream(split1).collect(Collectors.toList());
                if (!collect.contains(t)) {
                    try {
                        ossFileService.delete(t);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (StrUtil.isNotBlank(albumPics) && StrUtil.isBlank(albumPics1)) {
            String[] split = albumPics.split(",");
            Arrays.stream(split).forEach(t -> {
                try {
                    ossFileService.delete(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return i;
    }

    @Override
    public Integer publishStatus(List<Long> ids, Integer publishStatus) {
        return pmsProductDao.updatePublishStatusByIds(ids,publishStatus);
    }

    @Override
    public Integer recommendStatus(List<Long> ids, Integer recommendStatus) {
        return pmsProductDao.updateRecommendStatusByIds(ids,recommendStatus);
    }

    @Override
    public Integer newStatus(List<Long> ids, Integer newStatus) {
        return pmsProductDao.updateNewStatusByIds(ids, newStatus);
    }


}
