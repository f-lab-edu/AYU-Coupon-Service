package com.ayuconpon.usercoupon.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueUserCouponResponse {

    private Long issuedUserCouponId;

    public IssueUserCouponResponse(Long issuedUserCouponId) {
        this.issuedUserCouponId = issuedUserCouponId;
    }

}
