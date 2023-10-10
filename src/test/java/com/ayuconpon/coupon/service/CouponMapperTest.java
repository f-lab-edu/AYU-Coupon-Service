package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.util.Coupons;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponMapperTest {

    @Autowired
    private CouponMapper couponMapper;

    @DisplayName("쿠폰 엔티티를 쿠폰 Dto로 변환한다.")
    @Test
    public void  convertEntityToDto() {
        //given
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCoupon();
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();

        //when
        CouponDto fixCouponDto = couponMapper.toCouponDto(fixDiscountCoupon);
        CouponDto rateCouponDto = couponMapper.toCouponDto(rateDiscountCoupon);

        //then
        assertThat(fixCouponDto)
                .extracting("id", "name", "discountType", "discountContent", "leftQuantity", "minProductPrice", "startedAt", "finishedAt", "status")
                .containsExactly(
                        fixDiscountCoupon.getCouponId(),
                        fixDiscountCoupon.getName(),
                        fixDiscountCoupon.getDiscountPolicy().getDiscountType(),
                        fixDiscountCoupon.getDiscountPolicy().getDiscountPrice().getValue().toString(),
                        fixDiscountCoupon.getQuantity().getLeftQuantity(),
                        fixDiscountCoupon.getMinProductPrice().getValue(),
                        fixDiscountCoupon.getIssuePeriod().getStartedAt().toString(),
                        fixDiscountCoupon.getIssuePeriod().getFinishedAt().toString(),
                        CouponMapper.IN_PROGRESS
                );

        assertThat(rateCouponDto)
                .extracting("id", "name", "discountType", "discountContent", "leftQuantity", "minProductPrice", "startedAt", "finishedAt", "status")
                .containsExactly(
                        fixDiscountCoupon.getCouponId(),
                        rateDiscountCoupon.getName(),
                        rateDiscountCoupon.getDiscountPolicy().getDiscountType(),
                        rateDiscountCoupon.getDiscountPolicy().getDiscountRate().toString(),
                        rateDiscountCoupon.getQuantity().getLeftQuantity(),
                        rateDiscountCoupon.getMinProductPrice().getValue(),
                        rateDiscountCoupon.getIssuePeriod().getStartedAt().toString(),
                        rateDiscountCoupon.getIssuePeriod().getFinishedAt().toString(),
                        CouponMapper.IN_PROGRESS
                );

    }

}
