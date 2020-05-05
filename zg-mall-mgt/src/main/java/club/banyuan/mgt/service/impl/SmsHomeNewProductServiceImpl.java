package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.SmsHomeNewProductDao;
import club.banyuan.mgt.dao.entity.SmsHomeNewProduct;
import club.banyuan.mgt.dao.entity.SmsHomeNewProductExample;
import club.banyuan.mgt.dto.SmsHomeNewProductResp;
import club.banyuan.mgt.service.SmsHomeNewProductService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.SMS_HOME_NEW_PRODUCT_NOT_EXIST;
import static club.banyuan.mgt.common.FailReason.SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST;

@Service
public class SmsHomeNewProductServiceImpl implements SmsHomeNewProductService {

    @Autowired
    private SmsHomeNewProductDao smsHomeNewProductDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String productName, Integer recommendStatus) {
        PageHelper.startPage(pageNum, pageSize);
        SmsHomeNewProductExample smsHomeNewProductExample = new SmsHomeNewProductExample();
        SmsHomeNewProductExample.Criteria criteria = smsHomeNewProductExample.createCriteria();
        if (StrUtil.isNotBlank(productName)) {
            criteria.andProductNameLike(StrUtil.concat(true, "%", productName, "%"));
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        smsHomeNewProductExample.setOrderByClause("`sort` DESC,'id' ASC");
        List<SmsHomeNewProduct> smsHomeNewProducts = smsHomeNewProductDao.selectByExample(smsHomeNewProductExample);
        PageInfo<SmsHomeNewProduct> pageInfo = new PageInfo<>(smsHomeNewProducts);
        List<SmsHomeNewProductResp> collect = smsHomeNewProducts.stream().map(t -> {
            SmsHomeNewProductResp smsHomeNewProductResp = new SmsHomeNewProductResp();
            BeanUtil.copyProperties(t, smsHomeNewProductResp);
            return smsHomeNewProductResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach(t -> {
            SmsHomeNewProduct smsHomeNewProduct = smsHomeNewProductDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeNewProduct)) {
                throw new RequestFailException(SMS_HOME_NEW_PRODUCT_NOT_EXIST);
            }
        });
        return smsHomeNewProductDao.updateRecommendStatusByIds(ids, recommendStatus);
    }

    @Override
    public Integer create(List<SmsHomeNewProduct> smsHomeNewProducts) {
        smsHomeNewProducts.forEach(t -> {
            t.setSort(0);
            t.setRecommendStatus(1);
            smsHomeNewProductDao.insert(t);
        });
        return 1;
    }

    @Override
    public Integer updateSort(Integer sort, Long id) {
        SmsHomeNewProduct smsHomeNewProduct = smsHomeNewProductDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsHomeNewProduct)) {
            throw new RequestFailException(SMS_HOME_NEW_PRODUCT_NOT_EXIST);
        }
        smsHomeNewProduct.setSort(sort);
        return smsHomeNewProductDao.updateByPrimaryKey(smsHomeNewProduct);
    }

    @Override
    public Integer delete(List<Long> ids) {
        ids.forEach(t -> {
            SmsHomeNewProduct smsHomeNewProduct = smsHomeNewProductDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeNewProduct)) {
                throw new RequestFailException(SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST);
            }
        });
        return smsHomeNewProductDao.deleteByids(ids);
    }
}
