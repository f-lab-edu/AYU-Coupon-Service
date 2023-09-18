package com.ayuconpon.coupon.domain.entity;

import com.ayuconpon.common.BaseEntity;
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
    private Long id;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "coupon_rule_id")
    private CouponRule couponRule;
    @Column(name = "expired_at")
    private LocalDateTime expireDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Coupon(Long userId, CouponRule couponRule, LocalDateTime expireDate, Status status) {
        this.userId = userId;
        this.couponRule = couponRule;
        this.expireDate = expireDate;
        this.status = status;
    }

}
