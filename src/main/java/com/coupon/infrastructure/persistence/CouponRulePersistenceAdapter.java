package com.coupon.infrastructure.persistence;

import com.coupon.application.port.out.CouponRuleRepository;
import com.coupon.domain.core.CouponRule;
import com.coupon.domain.value.CouponRuleId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponRulePersistenceAdapter implements CouponRuleRepository {
    private final SpringDataCouponRuleRepository repository;

    @Override
    public Optional<CouponRule> findBy(CouponRuleId couponRuleId) {
        return repository.findById(couponRuleId);
    }

    @Override
    public boolean save(CouponRule couponRule) {
        repository.save(couponRule);
        return true;
    }
}
