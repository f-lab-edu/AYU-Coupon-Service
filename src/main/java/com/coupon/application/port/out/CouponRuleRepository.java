package com.coupon.application.port.out;

import com.coupon.domain.core.CouponRule;
import com.coupon.domain.value.CouponRuleId;

import java.util.Optional;

public interface CouponRuleRepository {

    public Optional<CouponRule> findBy(CouponRuleId couponRuleId);

    public boolean save(CouponRule couponRule);

}
