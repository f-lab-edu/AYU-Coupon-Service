package com.ayuconpon.common.warmup;

import com.ayuconpon.coupon.controller.CouponController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CouponWarmupRunner {

    private final CouponController couponController;

    private final Logger log = LoggerFactory.getLogger(CouponWarmupRunner.class);
    private static final Integer WARM_UP_COUNT = 5000;

    @EventListener(ApplicationReadyEvent.class)
    public void warmup() {
        Pageable pageable = PageRequest.of(0, 10);
        log.info("start Coupon warm up");
        try {
            IntStream.rangeClosed(1, WARM_UP_COUNT).forEach(i -> {
                couponController.showCoupons(pageable);
            });
        } catch (Exception e) {
            log.warn("UserCouponWarmupRunner caught exception at inner. ex {}", e.getMessage());
        }
        log.info("finish Coupon warm up");
    }

}
