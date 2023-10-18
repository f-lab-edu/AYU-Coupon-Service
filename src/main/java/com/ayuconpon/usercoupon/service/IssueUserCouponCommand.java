package com.ayuconpon.usercoupon.service;

import org.springframework.util.Assert;

public record IssueUserCouponCommand(Long userId, Long couponId){

    public IssueUserCouponCommand {
        Assert.notNull(userId, "user id가 비어있습니다.");
        Assert.notNull(couponId, "coupon id가 비어있습니다.");
    }

}
