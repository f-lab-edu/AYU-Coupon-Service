package com.coupon.application.port.out;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.value.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository {

    public List<Coupon> findAllBy(UserId userId);

    public boolean save(Coupon coupon);

}
