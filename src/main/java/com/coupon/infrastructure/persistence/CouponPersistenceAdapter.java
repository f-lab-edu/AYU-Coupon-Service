package com.coupon.infrastructure.persistence;

import com.coupon.application.port.out.CouponRepository;
import com.coupon.domain.core.Coupon;
import com.coupon.domain.value.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponPersistenceAdapter implements CouponRepository {
    private final SpringDataCouponRepository repository;
    @Override
    public List<Coupon> findAllBy(UserId userId) {
        return repository.findCouponsByUserId(userId);
    }

    @Override
    public boolean save(Coupon coupon) {
        repository.save(coupon);
        return true;
    }
}
