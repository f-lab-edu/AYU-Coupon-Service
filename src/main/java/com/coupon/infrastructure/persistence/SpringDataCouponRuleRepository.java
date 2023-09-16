package com.coupon.infrastructure.persistence;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponRule;
import com.coupon.domain.value.CouponId;
import com.coupon.domain.value.CouponRuleId;
import com.coupon.domain.value.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SpringDataCouponRuleRepository extends JpaRepository<CouponRule, CouponRuleId> {

    Optional<CouponRule> findById(CouponRuleId couponRuleId);
}
