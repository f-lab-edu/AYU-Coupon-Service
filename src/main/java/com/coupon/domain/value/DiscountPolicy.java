package com.coupon.domain.value;

import com.coupon.domain.common.MoneyConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

// 도메인 엔티티가 데이터베이스와 강결합되어 있음
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

    protected DiscountPolicy(DiscountType discountType, Long discountRate, Money discountValue) {
        this.discountType = discountType;
        this.discountRate = discountRate;
        this.discountValue = discountValue;
    }

    public static DiscountPolicy of(DiscountType discountType, Long discountRate, Money discountValue) {
        return new DiscountPolicy(discountType,discountRate, discountValue);
    }

    public static DiscountPolicy copy(DiscountPolicy discountPolicy) {
        return new DiscountPolicy(discountPolicy.discountType, discountPolicy.discountRate, discountPolicy.discountValue);
    }

    // 추상화를 사용하여 리팩토링 할 수 없을까?
    public Money calculateDiscountedPrice(Money productPrice) {
        if (discountType.equals(DiscountType.FIX_DISCOUNT)) {
            return productPrice.minus(discountValue);
        }
        if (discountType.equals(DiscountType.RATE_DISCOUNT)) {
            Money discountPrice = productPrice.multiply(BigDecimal.valueOf(discountRate));
            return productPrice.minus(discountPrice);
        }
        throw new IllegalStateException("할인 정책이 존재하지 않습니다");
    }

}
