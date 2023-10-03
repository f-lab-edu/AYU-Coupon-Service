package com.ayuconpon.coupon.domain.value;

import com.ayuconpon.common.Money;
import com.ayuconpon.common.MoneyConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountPolicy {

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;
    @Column(name = "discount_rate")
    private Double discountRate;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "discount_price")
    private Money discountPrice;

    private DiscountPolicy(DiscountType discountType, Double discountRate, Money discountPrice) {
        this.discountType = discountType;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
    }

    public static DiscountPolicy of(DiscountType discountType, Double discountRate, Money discountPrice) {
        return new DiscountPolicy(discountType, discountRate, discountPrice);
    }

}
