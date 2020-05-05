package club.banyuan.mgt.service;

import club.banyuan.mgt.common.ResponsePages;
import club.banyuan.mgt.dto.CreateCouponReq;

public interface SmsCouponService {
    ResponsePages list(Integer pageNum, Integer pageSize, String name, Integer type);

    Integer create(CreateCouponReq createCouponReq);

    CreateCouponReq getInfo(Long couponId);

    Integer update(Long couponId, CreateCouponReq createCouponReq);

    Integer delete(Long couponId);
}
