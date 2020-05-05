package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.SmsHomeRecommendProductDao;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendProduct;
import club.banyuan.mgt.dao.entity.SmsHomeRecommendProductExample;
import club.banyuan.mgt.dto.SmsHomeRecommendProductResp;
import club.banyuan.mgt.service.SmsHomeRecommendProductService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.SMS_HOME_RECOMMEND_PRODUCTT_NOT_EXIST;
import static club.banyuan.mgt.common.FailReason.SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST;

@Service
public class SmsHomeRecommendProductServiceImpl implements SmsHomeRecommendProductService {

    @Autowired
    private SmsHomeRecommendProductDao smsHomeRecommendProductDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String productName, Integer recommendStatus) {
        PageHelper.startPage(pageNum, pageSize);
        SmsHomeRecommendProductExample smsHomeRecommendProductExample = new SmsHomeRecommendProductExample();
        SmsHomeRecommendProductExample.Criteria criteria = smsHomeRecommendProductExample.createCriteria();
        if (StrUtil.isNotBlank(productName)){
            criteria.andProductNameLike(StrUtil.concat(true, "%",productName,"%"));
        }
        if (recommendStatus!=null){
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        smsHomeRecommendProductExample.setOrderByClause("`sort` DESC,'id' ASC");
        List<SmsHomeRecommendProduct> smsHomeRecommendProducts = smsHomeRecommendProductDao.selectByExample(smsHomeRecommendProductExample);
        PageInfo<SmsHomeRecommendProduct> pageInfo = new PageInfo<>(smsHomeRecommendProducts);
        List<SmsHomeRecommendProductResp> collect = smsHomeRecommendProducts.stream().map(t -> {
            SmsHomeRecommendProductResp smsHomeRecommendProductResp = new SmsHomeRecommendProductResp();
            BeanUtil.copyProperties(t, smsHomeRecommendProductResp);
            return smsHomeRecommendProductResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo,collect);
    }

    @Override
    public Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach(t->{
            SmsHomeRecommendProduct smsHomeRecommendProduct = smsHomeRecommendProductDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeRecommendProduct)){
                throw new RequestFailException(SMS_HOME_RECOMMEND_PRODUCTT_NOT_EXIST);
            }
        });
        return smsHomeRecommendProductDao.updateRecommendStatusByIds(ids,recommendStatus);
    }

    @Override
    public Integer create(List<SmsHomeRecommendProduct> smsHomeRecommendProducts) {
        smsHomeRecommendProducts.forEach(t->{
            t.setSort(0);
            t.setRecommendStatus(1);
            smsHomeRecommendProductDao.insert(t);
        });
        return 1;
    }

    @Override
    public Integer updateSort(Integer sort, Long id) {
        SmsHomeRecommendProduct smsHomeRecommendProduct = smsHomeRecommendProductDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsHomeRecommendProduct)){
            throw new RequestFailException(SMS_HOME_RECOMMEND_PRODUCTT_NOT_EXIST);
        }
        smsHomeRecommendProduct.setSort(sort);
        return smsHomeRecommendProductDao.updateByPrimaryKey(smsHomeRecommendProduct);
    }

    @Override
    public Integer delete(List<Long> ids) {
        ids.forEach(t->{
            SmsHomeRecommendProduct smsHomeRecommendProduct = smsHomeRecommendProductDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeRecommendProduct)){
                throw new RequestFailException(SMS_HOME_RECOMMEND_SUBJECT_NOT_EXIST);
            }
        });
        return smsHomeRecommendProductDao.deleteByids(ids);
    }
}
