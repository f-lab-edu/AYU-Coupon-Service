package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.BaseEntity;
import com.ayuconpon.common.Money;
import com.ayuconpon.common.MoneyConverter;
import com.ayuconpon.coupon.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(name = "name")
    private String name;
    @Embedded
    private DiscountPolicy discountPolicy;
    @Embedded
    private Quantity quantity;
    @Embedded
    private IssuePeriod issuePeriod;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "min_product_price")
    private Money minProductPrice;
    @Column(name = "usage_hours")
    private Long usageHours;

    public Coupon(String name, DiscountPolicy discountPolicy, Quantity quantity, IssuePeriod issuePeriod, Money minProductPrice, Long usageHours) {
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.quantity = quantity;
        this.issuePeriod = issuePeriod;
        this.minProductPrice = minProductPrice;
        this.usageHours = usageHours;
    }

    public void decrease(LocalDateTime currentTime) {
        issuePeriod.validate(currentTime);
        quantity.decrease();
    }

    public Money apply(Money productPrice) {
        if (!productPrice.isGreaterThanOrEqualTo(minProductPrice)) {
            throw new IllegalArgumentException("상품 금액이 쿠폰 적용 가능한 최소 금액보다 낮습니다.");
        }
        return discountPolicy.calculateDiscountedProductPrice(productPrice);
    }

}
