package com.ayucoupon.usercoupon.service.issue;

import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IssueUserCouponModuleImpl implements IssueUserCouponModule {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    @Transactional
    public Long issue(IssueUserCouponCommand command) {
        UserCoupon issuedUserCoupon = issueUserCoupon(command);
        return saveCoupon(issuedUserCoupon);
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

}
