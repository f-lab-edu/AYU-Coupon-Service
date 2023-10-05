package com.ayuconpon.usercoupon.domain;

import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    List<UserCoupon> findByUserId(Long userId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select uc from UserCoupon uc where uc.userCouponId=:userCouponId and uc.userId=:userId")
    Optional<UserCoupon> findByIdWithPessimisticLock(Long userCouponId, Long userId);

}
