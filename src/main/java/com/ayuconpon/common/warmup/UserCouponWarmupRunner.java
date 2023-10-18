package com.ayuconpon.common.warmup;

import com.ayuconpon.common.exception.AlreadyUsedUserCouponException;
import com.ayuconpon.common.exception.DuplicatedCouponException;
import com.ayuconpon.usercoupon.controller.UserCouponController;
import com.ayuconpon.usercoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.usercoupon.controller.request.UseUserCouponRequest;
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
public class UserCouponWarmupRunner {

    private final UserCouponController userCouponController;

    private final Logger log = LoggerFactory.getLogger(UserCouponWarmupRunner.class);
    private static final Long ADMIN_USER_ID = 1L;
    private static final Integer WARM_UP_COUNT = 5000;
    private static final Long REQUEST_COUPON_ID = 1L;
    private static final Long REQUEST_PRODUCT_PRICE = 10000L;

    @EventListener(ApplicationReadyEvent.class)
    public void warmup() {
        log.info("start UserCoupon warm up");
        try {
            IntStream.rangeClosed(1, WARM_UP_COUNT).forEach(i -> {
                warmupShowUserCoupons();
                warmupIssueCoupon();
                warmupUseCoupon();
            });
        } catch (Exception e) {
            log.warn("UserCouponWarmupRunner caught exception at inner. ex {}", e.getMessage());
        }
        log.info("finish UserCoupon warm up");
    }

    private void warmupShowUserCoupons() {
        Pageable pageable = PageRequest.of(0, 10);
        userCouponController.showUserCoupons(ADMIN_USER_ID, pageable);
    }

    private void warmupIssueCoupon() {
        try {
            userCouponController.issueCoupon(ADMIN_USER_ID, new IssueUserCouponRequest(REQUEST_COUPON_ID));
        } catch (DuplicatedCouponException ignored) {}
    }

    private void warmupUseCoupon() {
        try {
            userCouponController.useCoupon(ADMIN_USER_ID, REQUEST_COUPON_ID,new UseUserCouponRequest(REQUEST_PRODUCT_PRICE));
        } catch (AlreadyUsedUserCouponException ignored) {}
    }

}
