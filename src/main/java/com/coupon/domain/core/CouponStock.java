package com.coupon.domain.core;

import com.coupon.domain.exception.NonValidIssuePriodException;
import com.coupon.domain.util.TimeBasedUniqueIdGenerator;
import com.coupon.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_stock")
public class CouponStock {

    @EmbeddedId
    private CouponStockId id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_rule_id")
    private CouponRule couponRule;

    @Embedded
    private Quantity quantity;

    public static CouponStock newInstance(CouponRule couponRule, Quantity quantity) {
        return new CouponStock(
                CouponStockId.of(TimeBasedUniqueIdGenerator.getInstance().gen()),
                couponRule,
                quantity);
    }

    public Coupon issueCoupon(UserId userId, LocalDateTime issueTime) {
        if (!couponRule.isValidIssuePeriodTime(issueTime)) {
            throw new NonValidIssuePriodException("쿠폰 발급기간이 아닙니다.");
        }

        quantity = quantity.minus(Quantity.of(1L));

        return Coupon.newInstance(
                CouponId.of(TimeBasedUniqueIdGenerator.getInstance().gen()),
                userId,
                id,
                couponRule,
                couponRule.calculateExpireDate(issueTime)
        );
    }

}
