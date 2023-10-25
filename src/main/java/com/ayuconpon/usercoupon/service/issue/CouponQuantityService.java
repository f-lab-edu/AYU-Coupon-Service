package com.ayuconpon.usercoupon.service.issue;

import com.ayuconpon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponQuantityService {

    private final CouponRepository couponRepository;

    public void decrease(IssueUserCouponCommand command, LocalDateTime currentTime) {
        int result = couponRepository.decrease(command.couponId(), currentTime);
        if (result != 1) throw new IllegalStateException("쿠폰의 재고가 없습니다.");
    }

}
