package club.banyuan.mgt.service.impl;

import club.banyuan.mgt.common.RequestFailException;
import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.SmsFlashPromotionDao;
import club.banyuan.mgt.dao.SmsFlashPromotionProductRelationDao;
import club.banyuan.mgt.dao.entity.SmsFlashPromotion;
import club.banyuan.mgt.dao.entity.SmsFlashPromotionExample;
import club.banyuan.mgt.dto.SmsFlashPromotionResp;
import club.banyuan.mgt.service.SmsFlashPromotionService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static club.banyuan.mgt.common.FailReason.SMS_FLASH_PROMOTION_NOT_EXIST;

@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService {

    @Autowired
    private SmsFlashPromotionDao smsFlashPromotionDao;
    @Autowired
    private SmsFlashPromotionProductRelationDao smsFlashPromotionProductRelationDao;

    @Override
    public ResponsePages list(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        SmsFlashPromotionExample smsFlashPromotionExample = new SmsFlashPromotionExample();
        SmsFlashPromotionExample.Criteria criteria = smsFlashPromotionExample.createCriteria();
        if (StrUtil.isNotBlank(keyword)) {
            criteria.andTitleLike(StrUtil.concat(true, "%", keyword, "%"));
        }
        List<SmsFlashPromotion> smsFlashPromotions = smsFlashPromotionDao.selectByExample(smsFlashPromotionExample);
        PageInfo<SmsFlashPromotion> pageInfo = new PageInfo<>(smsFlashPromotions);
        List<SmsFlashPromotionResp> collect = smsFlashPromotions.stream().map(t -> {

            SmsFlashPromotionResp smsFlashPromotionResp = new SmsFlashPromotionResp();
            BeanUtil.copyProperties(t, smsFlashPromotionResp);
            return smsFlashPromotionResp;
        }).collect(Collectors.toList());
        return ResponsePages.setPages(pageInfo, collect);
    }

    @Override
    public Integer create(SmsFlashPromotion smsFlashPromotion) {
        smsFlashPromotion.setCreateTime(new Date());
        return smsFlashPromotionDao.insert(smsFlashPromotion);
    }

    @Override
    public Integer updateStatus(Long id, Integer status) {
        SmsFlashPromotion smsFlashPromotion = smsFlashPromotionDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsFlashPromotion)) {
            throw new RequestFailException(SMS_FLASH_PROMOTION_NOT_EXIST);
        }
        smsFlashPromotion.setStatus(status);
        return smsFlashPromotionDao.updateByPrimaryKey(smsFlashPromotion);
    }

    @Override
    public Integer update(Long id, SmsFlashPromotion smsFlashPromotion) {
        SmsFlashPromotion smsFlashPromotion1 = smsFlashPromotionDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsFlashPromotion1)) {
            throw new RequestFailException(SMS_FLASH_PROMOTION_NOT_EXIST);
        }
        return smsFlashPromotionDao.updateByPrimaryKey(smsFlashPromotion);
    }

    @Override
    public Integer delete(Long id) {
        SmsFlashPromotion smsFlashPromotion1 = smsFlashPromotionDao.selectByPrimaryKey(id);
        if (BeanUtil.isEmpty(smsFlashPromotion1)) {
            throw new RequestFailException(SMS_FLASH_PROMOTION_NOT_EXIST);
        }
        smsFlashPromotionDao.deleteByPrimaryKey(id);
        return smsFlashPromotionProductRelationDao.deleteBySmsFlashProMotionId(id);
    }
}
