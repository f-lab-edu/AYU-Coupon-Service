package com.ayuconpon.common.config.warmup;

import com.ayuconpon.warmup.CouponWarmupRunner;
import com.ayuconpon.warmup.UserCouponWarmupRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CouponWarmupProperties.class, UserCouponWarmupProperties.class})
@RequiredArgsConstructor
public class WarmupConfig {

    private final CouponWarmupProperties couponWarmupProperties;
    private final UserCouponWarmupProperties userCouponWarmupProperties;

    @Bean
    public CouponWarmupRunner couponWarmupRunner() {
        return new CouponWarmupRunner(couponWarmupProperties);
    }

    @Bean
    public UserCouponWarmupRunner userCouponWarmupRunner() {
        return new UserCouponWarmupRunner(userCouponWarmupProperties);
    }

}
