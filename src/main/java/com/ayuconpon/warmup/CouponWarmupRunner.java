package com.ayuconpon.warmup;

import com.ayuconpon.common.config.warmup.CouponWarmupProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

public class CouponWarmupRunner {

    private static final Logger log = LoggerFactory.getLogger(CouponWarmupRunner.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private final Integer WARM_UP_COUNT;
    private final String COUPONS_URL;

    public CouponWarmupRunner(CouponWarmupProperties properties) {
        WARM_UP_COUNT = properties.getWarmupCount();
        COUPONS_URL = properties.getCouponUrl();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmup() {
        log.info("start Coupon warm up");
        try {
            IntStream.rangeClosed(1, WARM_UP_COUNT).forEach(i -> {
                restTemplate.getForObject(COUPONS_URL, String.class);
            });
        } catch (Exception e) {
            log.warn("CouponWarmupRunner caught exception at inner. ex {}", e.getMessage());
        }
        log.info("finish Coupon warm up");
    }

}
