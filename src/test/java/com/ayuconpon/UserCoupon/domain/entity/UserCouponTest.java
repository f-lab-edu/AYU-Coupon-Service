package com.ayuconpon.usercoupon.domain.entity;

import com.ayuconpon.common.Money;
import com.ayuconpon.common.exception.AlreadyUsedUserCouponException;
import com.ayuconpon.common.exception.ExpiredUserCouponException;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.DiscountPolicy;
import com.ayuconpon.coupon.domain.value.DiscountType;
import com.ayuconpon.coupon.domain.value.IssuePeriod;
import com.ayuconpon.coupon.domain.value.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserCouponTest {

    @DisplayName("쿠폰 적용 요청을 보낼 수 있다.")
    @Test
    public void issueUserCoupon() throws Exception {
        // given
        UserCoupon userCoupon = getDefaultUserCoupon();
        Money productPrice = Money.wons(10000L);
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);

        // when
        Money discountedProductPrice = userCoupon.use(productPrice, currentTime);

        // then
        assertThat(discountedProductPrice.getValue()).isEqualTo(Money.wons(9000L).getValue());
    }

    @DisplayName("사용된 쿠폰은 사용할 수 없다.")
    @Test
    public void applyAlreadyUsedUserCoupon () {
        //given
        UserCoupon userCoupon = getDefaultUserCoupon();
        Money productPrice = Money.wons(10000L);
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);

        userCoupon.use(productPrice, currentTime);

        // when //then
        assertThatThrownBy(() -> userCoupon.use(productPrice, currentTime))
                .isInstanceOf(AlreadyUsedUserCouponException.class)
                .hasMessage("이미 사용한 쿠폰입니다.");
     }

    @DisplayName("사용기간이 지난 쿠폰은 사용할 수 없다.")
    @Test
    public void applyExpiredUserCoupon () {
        //given
        UserCoupon userCoupon = getDefaultUserCoupon();
        Money productPrice = Money.wons(10000L);
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 26, 12, 0, 1);

        // when //then
        assertThatThrownBy(() -> userCoupon.use(productPrice, currentTime))
                .isInstanceOf(ExpiredUserCouponException.class)
                .hasMessage("만료된 쿠폰입니다.");
    }


    private UserCoupon getDefaultUserCoupon() {
        Long userId = 1L;
        Coupon coupon = getDefaultFixDiscountCoupon();
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 23, 12, 0, 0);
        return new UserCoupon(userId, coupon, currentTime);

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


}
