package com.coupon.infrastructure.persistence;

import com.coupon.application.port.out.CouponRepository;
import com.coupon.application.port.out.CouponStockRepository;
import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.value.CouponStockId;
import com.coupon.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponStockPersistenceAdapter implements CouponStockRepository {
    private final SpringDataCouponStockRepository repository;


    @Override
    public Optional<CouponStock> findBy(CouponStockId couponStockId) {
        return repository.findById(couponStockId);
    }

    @Override
    public boolean save(CouponStock couponStock) {
        repository.save(couponStock);
        return true;
    }
}
