package com.ayuconpon.coupon.domain;

import com.ayuconpon.coupon.domain.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    Optional<UserCoupon> findByCouponCouponIdAndUserId(Long couponId, Long userId);

}
