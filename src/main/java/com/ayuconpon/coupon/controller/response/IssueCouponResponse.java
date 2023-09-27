package com.ayuconpon.coupon.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCouponResponse {

    public Long issuedCouponId;

    public IssueCouponResponse(Long issuedCouponId) {
        this.issuedCouponId = issuedCouponId;
    }

}
