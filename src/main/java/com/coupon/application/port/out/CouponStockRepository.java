package com.coupon.application.port.out;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.value.CouponStockId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponStockRepository {

    public Optional<CouponStock> findBy(CouponStockId couponStockId);

    public boolean save(CouponStock couponStock);
    
}
