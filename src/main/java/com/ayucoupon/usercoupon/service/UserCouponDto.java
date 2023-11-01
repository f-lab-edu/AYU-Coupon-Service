package com.ayucoupon.usercoupon.service;

import com.ayucoupon.coupon.domain.value.DiscountType;
import com.ayucoupon.usercoupon.domain.value.Status;
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
