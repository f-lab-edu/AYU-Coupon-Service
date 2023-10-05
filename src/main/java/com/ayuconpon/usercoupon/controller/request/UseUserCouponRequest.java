package com.ayuconpon.usercoupon.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UseUserCouponRequest {

    @NotNull(message = "쿠폰 아이디가 비어있습니다.")
    private Long userCouponId;

    @NotNull(message = "상품 가격이 비어있습니다.")
    private Long productPrice;

    public UseUserCouponRequest(Long userCouponId, Long productPrice) {
        this.userCouponId = userCouponId;
        this.productPrice = productPrice;
    }

}
