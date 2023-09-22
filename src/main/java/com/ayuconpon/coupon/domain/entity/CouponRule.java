package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.BaseEntity;
import com.ayuconpon.common.Money;
import com.ayuconpon.common.MoneyConverter;
import com.ayuconpon.coupon.domain.value.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_rule")
public class CouponRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponRuleId;

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

    public CouponRule(String name, DiscountPolicy discountPolicy, Quantity quantity, IssuePeriod issuePeriod, Money minProductPrice, Long usageHours) {
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.quantity = quantity;
        this.issuePeriod = issuePeriod;
        this.minProductPrice = minProductPrice;
        this.usageHours = usageHours;
    }

}