package com.ayuconpon.usercoupon.service.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IssueUserCouponFacade {

    private final IssueUserCouponValidator validator;
    private final CouponQuantityService couponQuantityService;
    private final IssueUserCouponService issueUserCouponService;

    public Long issue(IssueUserCouponCommand command) {
        validator.validate(command);

        LocalDateTime currentTime = LocalDateTime.now();
        couponQuantityService.decrease(command, currentTime);
        return issueUserCouponService.issue(command, currentTime);
    }

}
