package com.ayuconpon.common.warmup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.IntStream;

@Component
public class UserCouponWarmupRunner {

    private static final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private static final Logger log = LoggerFactory.getLogger(UserCouponWarmupRunner.class);

    private static final Long ADMIN_USER_ID = 1L;
    private static final Integer WARM_UP_COUNT = 5000;

    private static final String USER_COUPONS_URL = "http://localhost:8080/v1/users/me/user-coupons";
    private static final String USE_USER_COUPONS_URL = "http://localhost:8080/v1/users/me/user-coupons/1";
    private static final String USE_COUPON_BODY = "{ \"productPrice\" : \"10000\" }";
    private static final String ISSUE_COUPON_BODY = "{ \"couponId\" : \"1\" }";


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
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        restTemplate.exchange(USER_COUPONS_URL, HttpMethod.GET, request, String.class);
    }

    private void warmupIssueCoupon() {
        HttpHeaders headers = getHeaders();
        HttpEntity<String> request = new HttpEntity<>(ISSUE_COUPON_BODY, headers);
        try {
            restTemplate.exchange(USER_COUPONS_URL, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException ignore) {}
    }

    private void warmupUseCoupon() {
        HttpHeaders headers = getHeaders();
        HttpEntity<String> request = new HttpEntity<>(USE_COUPON_BODY, headers);
        try {
            restTemplate.exchange(USE_USER_COUPONS_URL, HttpMethod.PATCH, request, String.class);
        } catch (HttpClientErrorException ignore) {}
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("User-Id", ADMIN_USER_ID.toString());
        return headers;
    }

}
