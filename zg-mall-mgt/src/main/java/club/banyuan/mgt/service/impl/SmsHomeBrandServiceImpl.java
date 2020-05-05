package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.SmsHomeBrandDao;
import club.banyuan.mgt.dao.entity.SmsHomeBrand;
import club.banyuan.mgt.dao.entity.SmsHomeBrandExample;
import club.banyuan.mgt.dto.SmsHomeBrandResp;
import club.banyuan.mgt.service.SmsHomeBrandService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.SMS_HOME_BRAND_NOT_EXIST;

@Service
public class SmsHomeBrandServiceImpl implements SmsHomeBrandService {

    @Autowired
    private SmsHomeBrandDao smsHomeBrandDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String brandName, Integer recommendStatus) {
        PageHelper.startPage(pageNum, pageSize);
        SmsHomeBrandExample smsHomeBrandExample = new SmsHomeBrandExample();
        SmsHomeBrandExample.Criteria criteria = smsHomeBrandExample.createCriteria();
        if (StrUtil.isNotBlank(brandName)) {
            criteria.andBrandNameLike(StrUtil.concat(true, "%", brandName, "%"));
        }
        if (recommendStatus != null) {
            criteria.andRecommendStatusEqualTo(recommendStatus);
        }
        smsHomeBrandExample.setOrderByClause("`sort` DESC,'id' ASC");
        List<SmsHomeBrand> smsHomeBrands = smsHomeBrandDao.selectByExample(smsHomeBrandExample);
        PageInfo<SmsHomeBrand> pageInfo = new PageInfo<>(smsHomeBrands);
        List<SmsHomeBrandResp> collect = smsHomeBrands.stream().map(t -> {
            SmsHomeBrandResp smsHomeBrandResp = new SmsHomeBrandResp();
            BeanUtil.copyProperties(t, smsHomeBrandResp);
            return smsHomeBrandResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public Integer create(List<SmsHomeBrand> smsHomeBrands) {
        smsHomeBrands.forEach(t->{
            t.setSort(0);
            t.setRecommendStatus(1);
            smsHomeBrandDao.insert(t);
        });
        return 1;
    }

    @Override
    public Integer updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ids.forEach(t->{
            SmsHomeBrand smsHomeBrand = smsHomeBrandDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeBrand)){
                throw new RequestFailException(SMS_HOME_BRAND_NOT_EXIST);
            }
        });
        return smsHomeBrandDao.updateRecommendStatusByIds(ids,recommendStatus);
    }

    @Override
    public Integer updateSort(Integer sort, Long id) {
        SmsHomeBrand smsHomeBrand = smsHomeBrandDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsHomeBrand)){
            throw new RequestFailException(SMS_HOME_BRAND_NOT_EXIST);
        }
        smsHomeBrand.setSort(sort);
        return smsHomeBrandDao.updateByPrimaryKey(smsHomeBrand);
    }

    @Override
    public Integer delete(List<Long> ids) {
        ids.forEach(t->{
            SmsHomeBrand smsHomeBrand = smsHomeBrandDao.selectByPrimaryKey(t);
            if (BeanUtil.isEmpty(smsHomeBrand)){
                throw new RequestFailException(SMS_HOME_BRAND_NOT_EXIST);
            }
        });
        return smsHomeBrandDao.deleteByids(ids);
    }
}
