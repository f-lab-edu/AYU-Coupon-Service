package com.coupon.domain.core;

import com.coupon.domain.common.MoneyConverter;
import com.coupon.domain.util.TimeBasedUniqueIdGenerator;
import com.coupon.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "coupon_rule")
public class CouponRule {

    @EmbeddedId
    private CouponRuleId id;

    @Embedded
    private IssuePeriod issuePeriod;
    @Embedded
    private DiscountPolicy discountPolicy;
    @Convert(converter = MoneyConverter.class)
    @Column(name = "min_product_price")
    private Money minProductPrice;
    @Column(name = "usage_hours")
    private Long usageHours;

    public static CouponRule newInstance(IssuePeriod issuePeriod, DiscountPolicy discountPolicy, Money minProductPrice, Long usageHours) {
        return new CouponRule(
                CouponRuleId.of(TimeBasedUniqueIdGenerator.getInstance().gen()),
                issuePeriod,
                discountPolicy,
                minProductPrice,
                usageHours);
    }

    public Money calculateProductPrice(Money productPrice) {
        return discountPolicy.calculateDiscountedPrice(productPrice);
    }

    public ExpireDate calculateExpireDate(LocalDateTime issueTime) {
        return ExpireDate.of(issueTime.plusHours(usageHours));
    }

    public boolean isSatisfyMinPrice(Money ProductPrice) {
        return ProductPrice.isGreaterThanOrEqualTo(minProductPrice);
    }

    public boolean isValidIssuePeriodTime(LocalDateTime now) {
        return issuePeriod.isValidTime(now);
    }

}
