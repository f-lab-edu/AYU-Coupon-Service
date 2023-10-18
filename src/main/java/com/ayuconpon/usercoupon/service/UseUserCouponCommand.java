package com.ayuconpon.usercoupon.service;

import com.ayuconpon.common.Money;
import org.springframework.util.Assert;

public record UseUserCouponCommand(Long userId, Long userCouponId, Money productPrice){

    public UseUserCouponCommand {
        Assert.notNull(userId, "user id가 비어있습니다.");
        Assert.notNull(userCouponId, "userCouponId가 비어있습니다.");
        Assert.notNull(productPrice, "product price가 비어있습니다.");
    }

}
