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
        Long fixCouponId = 1L;
        Long rateCouponId = 2L;
        Long usageHours = 72L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCouponWithQuantity(Quantity.of(0L));
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();
        UserCoupon fixUserCoupon = new UserCoupon(userId, fixCouponId, usageHours, currentTime);
        UserCoupon rateUserCoupon = new UserCoupon(userId, rateCouponId, usageHours, currentTime);

        //when
        UserCouponDto fixeUserCouponDto = userCouponMapper.toUserCouponDto(fixUserCoupon, fixDiscountCoupon);
        UserCouponDto rateUserCouponDto = userCouponMapper.toUserCouponDto(rateUserCoupon, rateDiscountCoupon);

        //then
        assertThat(fixeUserCouponDto)
                .extracting("userCouponId", "couponName", "discountType", "discountContent", "minProductPrice", "status", "expiredAt")
                .containsExactly(
                        fixUserCoupon.getUserCouponId(),
                        fixDiscountCoupon.getName(),
                        fixDiscountCoupon.getDiscountPolicy().getDiscountType(),
                        fixDiscountCoupon.getDiscountPolicy().getDiscountPrice().getValue().toString(),
                        fixDiscountCoupon.getMinProductPrice().getValue(),
                        fixUserCoupon.getStatus(),
                        fixUserCoupon.getExpiredAt().toString()
                );

        assertThat(rateUserCouponDto)
                .extracting("userCouponId", "couponName", "discountType", "discountContent", "minProductPrice", "status", "expiredAt")
                .containsExactly(
                        rateUserCoupon.getUserCouponId(),
                        rateDiscountCoupon.getName(),
                        rateDiscountCoupon.getDiscountPolicy().getDiscountType(),
                        rateDiscountCoupon.getDiscountPolicy().getDiscountRate().toString(),
                        rateDiscountCoupon.getMinProductPrice().getValue(),
                        rateUserCoupon.getStatus(),
                        rateUserCoupon.getExpiredAt().toString()
                );
    }

}
