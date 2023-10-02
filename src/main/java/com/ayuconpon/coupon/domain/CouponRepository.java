package com.ayuconpon.coupon.domain;

import com.ayuconpon.coupon.domain.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Coupon c where c.couponId=:couponId")
    Coupon findByIdWithPessimisticLock(Long couponId);

}
