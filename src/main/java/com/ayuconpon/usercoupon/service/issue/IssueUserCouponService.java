package com.ayuconpon.usercoupon.service.issue;

import com.ayuconpon.common.exception.NotFoundCouponException;
import com.ayuconpon.coupon.domain.CouponRepository;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.usercoupon.domain.UserCouponRepository;
import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IssueUserCouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public Long issue(IssueUserCouponCommand command, LocalDateTime currentTime) {
        Coupon coupon = couponRepository.findById(command.couponId())
                .orElseThrow(NotFoundCouponException::new);
        return saveCoupon(new UserCoupon(command.userId(), coupon, currentTime));
    }

    private Long saveCoupon(UserCoupon issuedCoupon) {
        return userCouponRepository.save(issuedCoupon).getUserCouponId();
    }

}
