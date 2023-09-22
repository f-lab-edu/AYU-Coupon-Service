package com.ayuconpon.coupon.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueCouponRequest {

    @NotNull(message = "쿠폰 발급 요청 아이디가 비어있습니다.")
    private Long couponStockId;

    public IssueCouponRequest(Long couponId) {
        this.couponStockId = couponId;
    }

}
