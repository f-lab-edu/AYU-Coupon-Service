package com.coupon.domain.core;

import com.coupon.domain.exception.ExpiredCouponException;
import com.coupon.domain.exception.NonSatisfyMinPriceException;
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
@Table(name = "coupon")
public class Coupon {

    @EmbeddedId
    private CouponId couponId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id"))
    private UserId userId;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "coupon_stock_id"))
    private CouponStockId couponStockId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_rule_id")
    private CouponRule couponRule;

    @Embedded
    private ExpireDate expireDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public static Coupon newInstance(CouponId couponId, UserId userid, CouponStockId couponStockId, CouponRule couponRule, ExpireDate expireDate) {
        return new Coupon(couponId, userid, couponStockId, couponRule, expireDate, Status.UNUSED);
    }

}
