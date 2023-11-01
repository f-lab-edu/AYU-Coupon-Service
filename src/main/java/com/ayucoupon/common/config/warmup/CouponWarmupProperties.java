package com.ayucoupon.common.config.warmup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("my.warmup.coupon")
public class CouponWarmupProperties {

    private final Integer warmupCount;
    private final String baseUrl;

}
