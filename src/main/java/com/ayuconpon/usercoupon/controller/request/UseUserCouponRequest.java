package com.ayuconpon.usercoupon.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseUserCouponRequest {

    @NotNull(message = "상품 가격이 비어있습니다.")
    private Long productPrice;

    public UseUserCouponRequest(Long productPrice) {
        this.productPrice = productPrice;
    }

}
