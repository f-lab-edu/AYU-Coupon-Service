package com.coupon.infrastructure.persistence;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.value.CouponId;
import com.coupon.domain.value.CouponStockId;
import com.coupon.domain.value.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SpringDataCouponStockRepository extends JpaRepository<CouponStock, CouponStockId> {

    Optional<CouponStock> findById(CouponStockId couponStockId);
}
