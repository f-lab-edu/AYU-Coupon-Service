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
    private Long discountRate;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "discount_value")
    private Money discountValue;

    private DiscountPolicy(DiscountType discountType, Long discountRate, Money discountValue) {
        this.discountType = discountType;
        this.discountRate = discountRate;
        this.discountValue = discountValue;
    }

    public static DiscountPolicy of(DiscountType discountType, Long discountRate, Money discountValue) {
        return new DiscountPolicy(discountType,discountRate, discountValue);
    }

}
