package com.ayuconpon.usercoupon.service;

import com.ayuconpon.coupon.domain.value.DiscountType;
import com.ayuconpon.usercoupon.domain.value.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCouponDto {

    private Long userCouponId;
    private String couponName;
    private DiscountType discountType;
    private String discountContent;
    private Long minProductPrice;
    private Status status;
    private String expiredAt;

}
