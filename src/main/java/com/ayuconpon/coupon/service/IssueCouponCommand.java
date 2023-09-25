package com.ayuconpon.coupon.service;

import org.springframework.util.Assert;

public record IssueCouponCommand (Long userId, Long couponId){

    public IssueCouponCommand {
        Assert.notNull(userId, "user id가 비어있습니다.");
        Assert.notNull(couponId, "coupon id가 비어있습니다.");
    }

}
