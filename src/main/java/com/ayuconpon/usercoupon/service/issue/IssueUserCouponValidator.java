package com.ayuconpon.usercoupon.service.issue;

import com.ayuconpon.common.exception.DuplicatedCouponException;
import com.ayuconpon.common.exception.NotFoundCouponException;
import com.ayuconpon.common.exception.RequireRegistrationException;
import com.ayuconpon.coupon.domain.CouponRepository;
import com.ayuconpon.user.domain.UserRepository;
import com.ayuconpon.usercoupon.domain.UserCouponRepository;
import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueUserCouponValidator {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public void validate(IssueUserCouponCommand command) {
        validateRegisteredCoupon(command);
        validateRegisteredUser(command);
        validateDuplicatedCoupon(command);
    }

    private void validateRegisteredCoupon(IssueUserCouponCommand command) {
        boolean isExist = couponRepository.existsById(command.couponId());
        if (!isExist) throw new NotFoundCouponException();
    }

    private void validateRegisteredUser(IssueUserCouponCommand command) {
        boolean isExist = userRepository.existsById(command.userId());
        if (!isExist) throw new RequireRegistrationException();
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
