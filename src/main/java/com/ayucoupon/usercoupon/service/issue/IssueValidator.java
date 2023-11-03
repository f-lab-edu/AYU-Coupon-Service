package com.ayucoupon.usercoupon.service.issue;

import com.ayucoupon.common.exception.DuplicatedCouponException;
import com.ayucoupon.common.exception.NotFoundCouponException;
import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IssueValidator {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional(readOnly = true)
    public void validate(IssueUserCouponCommand command) {
        validateRegisteredCoupon(command);
        validateDuplicatedCoupon(command);
    }

    private void validateRegisteredCoupon(IssueUserCouponCommand command) {
        boolean isExist = couponRepository.existsById(command.couponId());
        if (!isExist) throw new NotFoundCouponException();
    }

    private void validateDuplicatedCoupon(IssueUserCouponCommand command) {
        Optional<UserCoupon> userCoupon = userCouponRepository
                .findByUserId(command.userId())
                .stream()
                .filter(coupon -> coupon.getCouponId().equals(command.couponId()))
                .findAny();

        if (userCoupon.isPresent()) throw new DuplicatedCouponException();
    }

}
