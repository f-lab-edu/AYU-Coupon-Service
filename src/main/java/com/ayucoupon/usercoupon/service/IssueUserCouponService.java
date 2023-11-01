package com.ayucoupon.usercoupon.service;

import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import com.ayucoupon.common.exception.DuplicatedCouponException;
import com.ayucoupon.common.exception.NotFoundCouponException;
import com.ayucoupon.common.exception.RequireRegistrationException;
import com.ayucoupon.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class IssueUserCouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    public Long issue(IssueUserCouponCommand command) {
        validate(command);
        UserCoupon issuedUserCoupon = issueUserCoupon(command);
        return saveCoupon(issuedUserCoupon);
    }

    private void validate(IssueUserCouponCommand command) {
        validateRegisteredCoupon(command);
        validateDuplicatedCoupon(command);
    }

    private UserCoupon issueUserCoupon(IssueUserCouponCommand command) {
        Coupon coupon = couponRepository.findByIdWithPessimisticLock(command.couponId());

        LocalDateTime currentTime = LocalDateTime.now();

        coupon.decrease(currentTime);
        return new UserCoupon(command.userId(), coupon, currentTime);
    }

    private Long saveCoupon(UserCoupon issuedCoupon) {
        return userCouponRepository.save(issuedCoupon).getUserCouponId();
    }

    private void validateRegisteredCoupon(IssueUserCouponCommand command) {
        boolean isExist = couponRepository.existsById(command.couponId());
        if (!isExist) throw new NotFoundCouponException();
    }

    private void validateDuplicatedCoupon(IssueUserCouponCommand command) {
        Optional<UserCoupon> userCoupon = userCouponRepository
                .findByUserId(command.userId())
                .stream()
                .filter(coupon -> coupon.getCoupon().getCouponId().equals(command.couponId()))
                .findAny();

        if (userCoupon.isPresent()) throw new DuplicatedCouponException();
    }

}
