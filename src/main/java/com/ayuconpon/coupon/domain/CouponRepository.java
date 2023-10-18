package com.ayuconpon.coupon.domain;

import com.ayuconpon.coupon.domain.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Coupon c where c.couponId=:couponId")
    Coupon findByIdWithPessimisticLock(Long couponId);

    @Query("select c from Coupon c " +
            "where c.issuePeriod.startedAt <= :currentTime and :currentTime <= c.issuePeriod.finishedAt")
    List<Coupon> findCouponsInProgress(LocalDateTime currentTime, Pageable pageable);

}
