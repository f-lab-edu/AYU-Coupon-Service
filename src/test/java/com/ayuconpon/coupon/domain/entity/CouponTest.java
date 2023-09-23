package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.Money;
import com.ayuconpon.coupon.domain.value.DiscountPolicy;
import com.ayuconpon.coupon.domain.value.DiscountType;
import com.ayuconpon.coupon.domain.value.IssuePeriod;
import com.ayuconpon.coupon.domain.value.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    @DisplayName("쿠폰 발급 요청을 할 수 있다.")
    @Test
    public void issueUserCoupon() throws Exception {
        // given
        Quantity quantity = Quantity.of(100L);
        Coupon coupon = getDefaultFixDiscountCouponWithQuantity(quantity);

        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);

        // when
        UserCoupon issuedCoupon = coupon.issue(userId, currentTime);

        // then
        assertThat(issuedCoupon).isNotNull();
        assertThat(coupon.getQuantity().getLeftQuantity()).isEqualTo(99L);
    }

    @DisplayName("쿠폰 발급 기간이 지나면, 쿠폰 발급 요청을 할 수 없다.")
    @Test
    public void issueUserCouponAfterIssuePeriod() {
        // given
        Coupon coupon = getDefaultFixDiscountCoupon();

        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 1);

        // when then
        assertThatThrownBy(() -> coupon.issue(userId, currentTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 기간이 아닙니다.");
    }

    @DisplayName("쿠폰 발급 기간 전에는, 쿠폰 발급 요청을 할 수 없다.")
    @Test
    public void issueUserCouponBeforeIssuePeriod() {
        // given
        Coupon coupon = getDefaultFixDiscountCoupon();

        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 22, 23, 59, 59);

        // when then
        assertThatThrownBy(() -> coupon.issue(userId, currentTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급 기간이 아닙니다.");
    }

    @DisplayName("쿠폰 재고가 0이면, 쿠폰을 발급 받을 수 없다.")
    @Test
    public void issueUserCouponWithZeroQuantity() {
        // given
        Quantity quantity = Quantity.of(0L);
        Coupon coupon = getDefaultFixDiscountCouponWithQuantity(quantity);

        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);

        // when then
        assertThatThrownBy(() -> coupon.issue(userId, currentTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰의 재고가 없습니다.");
    }


    private Coupon getDefaultFixDiscountCoupon() {
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

    private Coupon getDefaultFixDiscountCouponWithQuantity(Quantity quantity) {
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
