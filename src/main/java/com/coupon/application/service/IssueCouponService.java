package com.coupon.application.service;

import com.coupon.application.port.in.*;
import com.coupon.application.port.out.CouponStockRepository;
import com.coupon.application.port.out.CouponRepository;
import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.exception.DuplicateIssueException;
import com.coupon.domain.exception.NotFoundCouponStockException;
import com.coupon.domain.value.CouponId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Service
public class IssueCouponService implements IssueCouponUseCase {
    private final CouponStockRepository couponStockRepository;
    private final CouponRepository couponRepository;
    @Override
    public CouponId issueCoupon(IssueCouponCommand command) {
        checkDuplicateIssue(command);

        CouponStock couponStock = findCouponStock(command);

        Coupon coupon = couponStock.issueCoupon(command.userId(), LocalDateTime.now());
        couponRepository.save(coupon);

        return coupon.getCouponId();
    }

    private CouponStock findCouponStock(IssueCouponCommand command) {
        return couponStockRepository.findBy(command.couponStockId())
                .orElseThrow(() -> new NotFoundCouponStockException("요청에 대한 쿠폰 재고가 없습니다."));
    }

    private void checkDuplicateIssue(IssueCouponCommand command) {
        List<Coupon> coupons = couponRepository.findAllBy(command.userId());
        if (coupons.stream().anyMatch(coupon -> coupon.getCouponStockId().equals(command.couponStockId()))) {
            throw new DuplicateIssueException("같은 쿠폰에 대해서 중복 발급할 수 없습니다.");
        }
    }

}
