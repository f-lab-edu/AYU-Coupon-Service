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
        LocalDateTime currentTime = LocalDateTime.now();
        Long userCouponId = issueUserCoupon(command, currentTime);
        decreaseCouponQuantity(command, currentTime);
        return userCouponId;
    }

    private Long issueUserCoupon(IssueUserCouponCommand command, LocalDateTime currentTime) {
        Long couponUsageHours = couponRepository.findCouponUsageHours(command.couponId());
        UserCoupon issuedCoupon = new UserCoupon(command.userId(), command.couponId(), couponUsageHours, currentTime);
        return userCouponRepository.save(issuedCoupon)
                .getUserCouponId();
    }

    private void decreaseCouponQuantity(IssueUserCouponCommand command, LocalDateTime currentTime) {
        Coupon coupon = couponRepository.findByIdWithPessimisticLock(command.couponId());
        coupon.decrease(currentTime);
    }

}
