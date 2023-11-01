package com.ayucoupon.usercoupon.service.issue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueUserCouponService {

    private final IssueUserCouponModule issueUserCouponModule;
    private final IssueValidator issueValidator;

    public Long issue(IssueUserCouponCommand command) {
        issueValidator.validate(command);
        return issueUserCouponModule.issue(command);
    }

}
