package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.DiscountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDto {

    private Long id;
    private String name;
    private DiscountType discountType;
    private String discountContent;
    private Long leftQuantity;
    private Long minProductPrice;
    private String status;
    private String startedAt;
    private String finishedAt;

}
