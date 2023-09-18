package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.BaseEntity;
import com.ayuconpon.common.Money;
import com.ayuconpon.common.MoneyConverter;
import com.ayuconpon.coupon.domain.value.*;
import jakarta.persistence.*;
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
    private Long id;

    @Column(name = "name")
    private String name;
    @Embedded
    private IssuePeriod issuePeriod;
    @Embedded
    private DiscountPolicy discountPolicy;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "min_product_price")
    private Money minProductPrice;
    @Column(name = "usage_hours")
    private Long usageHours;

    public CouponRule(String name, IssuePeriod issuePeriod, DiscountPolicy discountPolicy, Money minProductPrice, Long usageHours) {
        this.name = name;
        this.issuePeriod = issuePeriod;
        this.discountPolicy = discountPolicy;
        this.minProductPrice = minProductPrice;
        this.usageHours = usageHours;
    }

}
