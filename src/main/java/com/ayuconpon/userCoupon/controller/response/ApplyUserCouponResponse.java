package com.ayuconpon.userCoupon.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplyUserCouponResponse {

    private Long discountedProductPrice;

    public ApplyUserCouponResponse(Long discountedProductPrice) {
        this.discountedProductPrice = discountedProductPrice;
    }

}
