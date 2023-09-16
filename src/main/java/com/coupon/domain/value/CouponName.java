package com.coupon.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponName {

    private String couponName;

    protected CouponName(String couponName) {
        this.couponName = couponName;
    }

    public static CouponName of(String couponName) {
        return new CouponName(couponName);
    }

}
