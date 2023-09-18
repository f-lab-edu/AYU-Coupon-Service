package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.BaseEntity;
import com.ayuconpon.coupon.domain.value.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon_stock")
public class CouponStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "coupon_rule_id")
    private CouponRule couponRule;
    @Embedded
    private Quantity quantity;

    public CouponStock(CouponRule couponRule, Quantity quantity) {
        this.couponRule = couponRule;
        this.quantity = quantity;
    }

}
