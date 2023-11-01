package com.ayucoupon.usercoupon.service;

import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.coupon.domain.value.Quantity;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import com.ayucoupon.util.Coupons;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class UserCouponMapperTest {

    @Autowired
    private UserCouponMapper userCouponMapper;

    @DisplayName("사용자 쿠폰 엔티티를 사용자 쿠폰 Dto로 변환한다.")
    @Test
    public void convertEntityToDto() {
        //given
        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCouponWithQuantity(Quantity.of(0L));
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();
        UserCoupon fixUserCoupon = new UserCoupon(userId, fixDiscountCoupon, currentTime);
        UserCoupon rateUserCoupon = new UserCoupon(userId, rateDiscountCoupon, currentTime);

        //when
        UserCouponDto fixeUserCouponDto = userCouponMapper.toUserCouponDto(fixUserCoupon);
        UserCouponDto rateUserCouponDto = userCouponMapper.toUserCouponDto(rateUserCoupon);

        //then
        assertThat(fixeUserCouponDto)
                .extracting("userCouponId", "couponName", "discountType", "discountContent", "minProductPrice", "status", "expiredAt")
                .containsExactly(
                        fixUserCoupon.getUserCouponId(),
                        fixUserCoupon.getCoupon().getName(),
                        fixUserCoupon.getCoupon().getDiscountPolicy().getDiscountType(),
                        fixUserCoupon.getCoupon().getDiscountPolicy().getDiscountPrice().getValue().toString(),
                        fixUserCoupon.getCoupon().getMinProductPrice().getValue(),
                        fixUserCoupon.getStatus(),
                        fixUserCoupon.getExpiredAt().toString()
                );

        assertThat(rateUserCouponDto)
                .extracting("userCouponId", "couponName", "discountType", "discountContent", "minProductPrice", "status", "expiredAt")
                .containsExactly(
                        rateUserCoupon.getUserCouponId(),
                        rateUserCoupon.getCoupon().getName(),
                        rateUserCoupon.getCoupon().getDiscountPolicy().getDiscountType(),
                        rateUserCoupon.getCoupon().getDiscountPolicy().getDiscountRate().toString(),
                        rateUserCoupon.getCoupon().getMinProductPrice().getValue(),
                        rateUserCoupon.getStatus(),
                        rateUserCoupon.getExpiredAt().toString()
                );
    }

}
