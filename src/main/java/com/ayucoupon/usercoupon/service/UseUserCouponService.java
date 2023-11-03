package com.ayucoupon.usercoupon.service;

import com.ayucoupon.common.Money;
import com.ayucoupon.common.exception.NotFoundUserCouponException;
import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UseUserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Money use(UseUserCouponCommand command) {
        return applyUserCoupon(command);
    }

    private Money applyUserCoupon(UseUserCouponCommand command) {
        UserCoupon userCoupon = userCouponRepository.findByIdWithPessimisticLock(command.userCouponId(), command.userId())
                .orElseThrow(NotFoundUserCouponException::new);
        Coupon coupon = couponRepository.findById(userCoupon.getCouponId())
                .orElseThrow(NotFoundUserCouponException::new);

        LocalDateTime currentTime = LocalDateTime.now();
        userCoupon.use(currentTime);
        return coupon.apply(command.productPrice());
    }

}
