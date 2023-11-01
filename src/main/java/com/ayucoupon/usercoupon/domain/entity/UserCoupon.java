package com.ayucoupon.usercoupon.domain.entity;

import com.ayucoupon.common.BaseEntity;
import com.ayucoupon.common.Money;
import com.ayucoupon.common.exception.AlreadyUsedUserCouponException;
import com.ayucoupon.common.exception.ExpiredUserCouponException;
import com.ayucoupon.usercoupon.domain.value.Status;
import com.ayucoupon.coupon.domain.entity.Coupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "user_coupon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "coupon_id_and_user_id_unique",
                        columnNames= {"coupon_id", "user_id"}
                )
        }
)
public class UserCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Long userCouponId;

    @Column(name = "user_id")
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
    @Column(name = "used_at")
    private LocalDateTime usedAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    public UserCoupon(Long userId, Coupon coupon, LocalDateTime currentTime) {
        this.userId = userId;
        this.coupon = coupon;
        this.issuedAt = currentTime;
        this.expiredAt = currentTime.plusHours(coupon.getUsageHours());
        this.usedAt = null;
        this.status = Status.UNUSED;
    }

    public Money use(Money productPrice, LocalDateTime currentTime) {
        validate(currentTime);
        status = Status.USED;
        usedAt = currentTime;
        return coupon.apply(productPrice);
    }

    private void validate(LocalDateTime currentTime) {
        if (status.equals(Status.USED)) {
            throw new AlreadyUsedUserCouponException();
        }
        if (expiredAt.isBefore(currentTime)) {
            throw new ExpiredUserCouponException();
        }
    }

}
