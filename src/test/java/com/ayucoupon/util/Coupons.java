package com.ayucoupon.util;

import com.ayucoupon.common.Money;
import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.coupon.domain.value.DiscountPolicy;
import com.ayucoupon.coupon.domain.value.DiscountType;
import com.ayucoupon.coupon.domain.value.IssuePeriod;
import com.ayucoupon.coupon.domain.value.Quantity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coupons {

    public static Coupon getDefaultFixDiscountCoupon() {
        String name = "기본 쿠폰";
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.FIX_DISCOUNT,
                null,
                Money.wons(1000L));
        Quantity quantity = Quantity.of(100L);
        IssuePeriod issuePeriod = IssuePeriod.of(
                LocalDateTime.of(2023, 9, 23, 0, 0, 0),
                LocalDateTime.of(2023, 9, 24, 0, 0, 0));
        Money minProductPrice = Money.wons(5000L);
        Long usageHours = 72L;

        return new Coupon(
                name,
                discountPolicy,
                quantity,
                issuePeriod,
                minProductPrice,
                usageHours);
    }

    public static Coupon getDefaultRateDiscountCoupon() {
        String name = "기본 쿠폰";
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.RATE_DISCOUNT,
                new BigDecimal("0.1"),
                null);
        Quantity quantity = Quantity.of(100L);
        IssuePeriod issuePeriod = IssuePeriod.of(
                LocalDateTime.of(2023, 9, 23, 0, 0, 0),
                LocalDateTime.of(2023, 9, 24, 0, 0, 0));
        Money minProductPrice = Money.wons(5000L);
        Long usageHours = 72L;

        return new Coupon(
                name,
                discountPolicy,
                quantity,
                issuePeriod,
                minProductPrice,
                usageHours);
    }

    public static Coupon getDefaultFixDiscountCouponWithQuantity(Quantity quantity) {
        String name = "기본 쿠폰";
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.FIX_DISCOUNT,
                null,
                Money.wons(1000L));
        IssuePeriod issuePeriod = IssuePeriod.of(
                LocalDateTime.of(2023, 9, 23, 0, 0, 0),
                LocalDateTime.of(2023, 9, 24, 0, 0, 0));
        Money minProductPrice = Money.wons(5000L);
        Long usageHours = 72L;

        return new Coupon(
                name,
                discountPolicy,
                quantity,
                issuePeriod,
                minProductPrice,
                usageHours);
    }

}
