package com.ayuconpon.usercoupon.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseUserCouponResponse {

    private Long discountedProductPrice;

    public UseUserCouponResponse(Long discountedProductPrice) {
        this.discountedProductPrice = discountedProductPrice;
    }

}
