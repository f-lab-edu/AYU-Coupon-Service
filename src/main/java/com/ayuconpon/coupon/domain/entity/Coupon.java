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
@Table(
        name = "coupon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "coupon_rule_id_and_user_id_unique",
                        columnNames= {"coupon_rule_id", "user_id"}
                )
        }
)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "coupon_rule_id")
    private CouponRule couponRule;
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Coupon(Long userId, CouponRule couponRule, LocalDateTime issuedAt, LocalDateTime expiredAt, LocalDateTime usedAt, Status status) {
        this.userId = userId;
        this.couponRule = couponRule;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.usedAt = usedAt;
        this.status = status;
    }

}
