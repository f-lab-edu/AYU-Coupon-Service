package com.coupon.domain.core;

import com.coupon.domain.exception.NonValidIssuePriodException;
import com.coupon.domain.exception.OutOfCouponStockException;
import com.coupon.domain.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponStockTest {

    @DisplayName("쿠폰을 발급할 수 있다.")
    @Test
    public void issueCoupon() {
        //given
        IssuePeriod issuePeriod = getDefaultIssuePeriod();
        LocalDateTime requestIssueDate = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 0);
        Money minProductPrice = Money.wons(1000L);
        Long usageHours = 1L;
        UserId userId = UserId.of(1L);

        CouponStock couponStock = getDefaultCouponStock(issuePeriod, minProductPrice, usageHours, Quantity.of(10L));

        //when
        Coupon coupon = couponStock.issueCoupon(userId, requestIssueDate);

        //then
        assertThat(coupon.getCouponId()).isNotNull();
        assertThat(coupon.getUserId()).isEqualTo(userId);
        assertThat(coupon.getExpireDate()).isEqualTo(ExpireDate.of(requestIssueDate.plusHours(usageHours)));
        assertThat(coupon.getStatus()).isEqualTo(Status.UNUSED);

    }

    @DisplayName("쿠폰을 발급 하면 쿠폰 재고 수량이 감소한다.")
    @Test
    public void decreaseCouponStock() {
        //given
        IssuePeriod issuePeriod = getDefaultIssuePeriod();
        LocalDateTime requestIssueDate = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 0);

        UserId userId = UserId.of(1L);

        CouponStock couponStock = getDefaultCouponStock(issuePeriod, Quantity.of(10L));

        //when
        couponStock.issueCoupon(userId, requestIssueDate);

        //then
        assertThat(couponStock.getQuantity()).isEqualTo(Quantity.of(9L));
    }

    @DisplayName("발급기간이 아닐 때, 쿠폰은 발급할 수 없다.")
    @Test
    public void nonIssuePeriodCouponTest() {
        //given
        IssuePeriod issuePeriod = IssuePeriod.of(LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 30), LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 35));

        LocalDateTime requestIssueBeforeIssuePeriod = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 29);
        LocalDateTime requestIssueAfterIssuePeriod = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 36);
        UserId userId = UserId.of(1L);

        CouponStock couponStock = getDefaultCouponStock(issuePeriod, Quantity.of(10L));

        //when //then
        assertThatThrownBy(() -> couponStock.issueCoupon(userId, requestIssueBeforeIssuePeriod))
                .isInstanceOf(NonValidIssuePriodException.class)
                .hasMessage("쿠폰 발급기간이 아닙니다.");

        assertThatThrownBy(() -> couponStock.issueCoupon(userId, requestIssueAfterIssuePeriod))
                .isInstanceOf(NonValidIssuePriodException.class)
                .hasMessage("쿠폰 발급기간이 아닙니다.");
    }

    @DisplayName("쿠폰의 재고가 없을 때, 쿠폰 발급은 발급받을 수 없다.")
    @Test
    public void OutOfCouponStockTest() {
        //given
        IssuePeriod issuePeriod = getDefaultIssuePeriod();
        LocalDateTime requestIssueDate = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 30);
        UserId userId = UserId.of(1L);

        CouponStock couponStock = getDefaultCouponStock(issuePeriod, Quantity.of(0L));

        //when //then
        assertThatThrownBy(() -> couponStock.issueCoupon(userId, requestIssueDate))
                .isInstanceOf(OutOfCouponStockException.class)
                .hasMessage("쿠폰 재고가 없습니다.");

    }

    private IssuePeriod getDefaultIssuePeriod() {
        return IssuePeriod.of(
                LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 0),
                LocalDateTime.of(2023, Month.OCTOBER, 14, 23, 0));
    }

    private static CouponStock getDefaultCouponStock(IssuePeriod issuePeriod, Quantity quantity) {
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.FIX_DISCOUNT,
                null,
                Money.wons(1000));

        CouponRule couponRule = CouponRule.newInstance(
                issuePeriod,
                discountPolicy,
                Money.wons(1000L),
                72L);


        CouponStock couponStock = CouponStock.newInstance(
                couponRule,
                quantity);
        return couponStock;
    }

    private CouponStock getDefaultCouponStock(IssuePeriod issuePeriod, Money minProductPrice, Long usageHours, Quantity quantity) {
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.FIX_DISCOUNT,
                null,
                Money.wons(1000L));

        CouponRule couponRule = CouponRule.newInstance(
                issuePeriod,
                discountPolicy,
                minProductPrice,
                usageHours);

        CouponStock couponStock = CouponStock.newInstance(
                couponRule,
                quantity);
        return couponStock;
    }

}
