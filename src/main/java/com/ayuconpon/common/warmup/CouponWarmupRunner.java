package com.ayuconpon.common.warmup;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class CouponWarmupRunner {

    private final static Logger log = LoggerFactory.getLogger(CouponWarmupRunner.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final Integer WARM_UP_COUNT = 5000;
    private static final String COUPONS_URL = "http://localhost:8080/v1/coupons";


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
