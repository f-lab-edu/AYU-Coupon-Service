package com.ayuconpon.usercoupon.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueUserCouponRequest {

    @NotNull(message = "쿠폰 발급 요청 아이디가 비어있습니다.")
    private Long couponId;

    public IssueUserCouponRequest(Long couponId) {
        this.couponId = couponId;
    }

}
