package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.DiscountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDto {

    private Long id;
    private String name;
    private DiscountType discountType;
    private String discountContent;
    private Long quantity;
    private Long minProductPrice;
    private String status;
    private String issuePeriod;

    // todo 세부 로직 구현

    public static CouponDto from(Coupon coupon) {
        CouponDto couponDto = new CouponDto();
        couponDto.id = coupon.getCouponId();
        couponDto.name = coupon.getName();

        // 디미터 원칙 위배
        couponDto.discountType = coupon.getDiscountPolicy().getDiscountType();
        couponDto.discountContent = getDiscountContent(coupon);
        couponDto.quantity = coupon.getQuantity().getLeftQuantity();

        couponDto.minProductPrice = coupon.getMinProductPrice().getValue();
        couponDto.status = "진행중";
        couponDto.issuePeriod = "쿠폰 발급 기간";
        return couponDto;
    }

    private static String getDiscountContent(Coupon coupon) {
        if (coupon.getDiscountPolicy().getDiscountType() == DiscountType.FIX_DISCOUNT) {
            return coupon.getDiscountPolicy().getDiscountPrice().getValue().toString();
        }
        return coupon.getDiscountPolicy().getDiscountRate().toString();
    }

}
