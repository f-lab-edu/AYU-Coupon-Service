package com.ayuconpon.usercoupon.service;

import com.ayuconpon.common.Money;
import org.springframework.util.Assert;

public record ApplyUserCouponCommand (Long userId, Long userCouponId, Money productPrice){

    public ApplyUserCouponCommand {
        Assert.notNull(userId, "user id가 비어있습니다.");
        Assert.notNull(userCouponId, "userCouponId가 비어있습니다.");
        Assert.notNull(productPrice, "product price가 비어있습니다.");
    }

}
