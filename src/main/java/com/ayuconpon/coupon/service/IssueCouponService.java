package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.entity.UserCoupon;
import com.ayuconpon.coupon.domain.CouponRepository;
import com.ayuconpon.coupon.domain.UserCouponRepository;
import com.ayuconpon.exception.DuplicatedCouponException;
import com.ayuconpon.exception.NotFoundCouponException;
import com.ayuconpon.exception.RequireRegistrationException;
import com.ayuconpon.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueCouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    public Long issue(IssueCouponCommand command) {
        validate(command);
        UserCoupon issuedCoupon = issueCoupon(command);
        return saveCoupon(issuedCoupon);
    }

    private void validate(IssueCouponCommand command) {
        validateRegisteredUser(command);
        validateDuplicatedCoupon(command);
    }

    private UserCoupon issueCoupon(IssueCouponCommand command) {
        Coupon coupon = couponRepository.findByIdWithPessimisticLock(command.couponId());
        if (coupon == null) throw new NotFoundCouponException();

        LocalDateTime currentTime = LocalDateTime.now();

        coupon.issue(currentTime);
        return new UserCoupon(command.userId(), coupon, currentTime);
    }

    private Long saveCoupon(UserCoupon issuedCoupon) {
        return userCouponRepository.save(issuedCoupon).getUserCouponId();
    }

    private void validateRegisteredUser(IssueCouponCommand command) {
        userRepository.findById(command.userId())
                .orElseThrow(RequireRegistrationException::new);
    }

    private void validateDuplicatedCoupon(IssueCouponCommand command) {
        Optional<UserCoupon> userCoupon = userCouponRepository
                .findByCouponCouponIdAndUserId(command.couponId() ,command.userId());

        if (userCoupon.isPresent()) throw new DuplicatedCouponException();
    }

}
