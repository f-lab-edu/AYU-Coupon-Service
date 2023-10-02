package com.ayuconpon.userCoupon.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueUserCouponResponse {

    public Long issuedUserCouponId;

    public IssueUserCouponResponse(Long issuedUserCouponId) {
        this.issuedUserCouponId = issuedUserCouponId;
    }

}
