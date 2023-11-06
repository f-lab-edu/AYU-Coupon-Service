package com.ayucoupon.usercoupon.service.issue;

import com.ayucoupon.common.lock.UserExclusiveRunner;
import com.ayucoupon.common.aop.multidatasource.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class IssueUserCouponService {

    private final IssueUserCouponModule issueUserCouponModule;
    private final IssueValidator issueValidator;
    private final UserExclusiveRunner userExclusiveRunner;

    @DataSource("primary")
    public Long issue(IssueUserCouponCommand command) {
        return userExclusiveRunner.call(command.userId(),
                Duration.ofSeconds(30),
                () -> {
                    issueValidator.validate(command);
                    return issueUserCouponModule.issue(command);
                });
    }

}
