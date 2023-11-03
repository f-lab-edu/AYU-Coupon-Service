package com.ayucoupon.usercoupon.domain;

import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findByUserId(Long userId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select uc from UserCoupon uc where uc.userCouponId=:userCouponId and uc.userId=:userId")
    Optional<UserCoupon> findByIdWithPessimisticLock(Long userCouponId, Long userId);

    @Transactional(readOnly = true)
    @Query("select uc from UserCoupon uc " +
            "where :currentTime <= uc.expiredAt and uc.userId = :userId")
    List<UserCoupon> getUnexpiredUserCoupons(Long userId , LocalDateTime currentTime, Pageable pageable);

}
