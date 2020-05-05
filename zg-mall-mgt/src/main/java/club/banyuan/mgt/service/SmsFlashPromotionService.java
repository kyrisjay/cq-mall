package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dao.entity.SmsFlashPromotion;

public interface SmsFlashPromotionService {
    ResponsePages list(Integer pageNum, Integer pageSize, String keyword);

    Integer create(SmsFlashPromotion smsFlashPromotion);

    Integer updateStatus(Long id, Integer status);

    Integer update(Long id, SmsFlashPromotion smsFlashPromotion);

    Integer delete(Long id);
}
