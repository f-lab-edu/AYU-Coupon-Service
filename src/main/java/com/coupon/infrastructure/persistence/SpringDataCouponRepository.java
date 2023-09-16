package com.coupon.infrastructure.persistence;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.value.CouponId;
import com.coupon.domain.value.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SpringDataCouponRepository extends JpaRepository<Coupon, CouponId> {

    List<Coupon> findCouponsByUserId(UserId userId);
}
