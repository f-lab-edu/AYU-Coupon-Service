package com.ayucoupon.common.config.warmup;

import com.ayucoupon.warmup.WarmupRegistry;
import com.ayucoupon.warmup.WarmupRunner;
import com.ayucoupon.warmup.WarmupStarter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Configuration
@EnableConfigurationProperties({CouponWarmupProperties.class, UserCouponWarmupProperties.class})
@ConditionalOnProperty(prefix = "my.warmup", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class WarmupConfig {

    private final CouponWarmupProperties couponWarmupProperties;
    private final UserCouponWarmupProperties userCouponWarmupProperties;

    @Bean
    public WarmupRunner showCouponsWarmupRunner() {
        return WarmupRunner
                .create(HttpMethod.GET, couponWarmupProperties.getBaseUrl(), couponWarmupProperties.getWarmupCount());
    }

    @Bean
    public WarmupRunner showUserCouponsWarmupRunner() {
        return WarmupRunner
                .create(HttpMethod.GET, userCouponWarmupProperties.getBaseUrl(), userCouponWarmupProperties.getWarmupCount())
                .headers(getHeaders());
    }

    @Bean
    public WarmupRunner issueCouponWarmupRunner() {
        return WarmupRunner
                .create(HttpMethod.POST, userCouponWarmupProperties.getBaseUrl(), userCouponWarmupProperties.getWarmupCount())
                .headers(getHeaders())
                .bodyValue(userCouponWarmupProperties.getIssueApiBody());
    }

    @Bean
    public WarmupRunner useCouponWarmupRunner() {
        return WarmupRunner
                .create(HttpMethod.PATCH, userCouponWarmupProperties.getBaseUrl(), userCouponWarmupProperties.getWarmupCount())
                .headers(getHeaders())
                .pathSegment(userCouponWarmupProperties.getUseApiPathSegment().toString())
                .bodyValue(userCouponWarmupProperties.getUseApiBody());
    }

    @Bean
    public WarmupRegistry warmupRegistry() {
        WarmupRegistry registry = new WarmupRegistry();
        registry.addWarmupRunner(showCouponsWarmupRunner())
                .addWarmupRunner(showUserCouponsWarmupRunner())
                .addWarmupRunner(issueCouponWarmupRunner())
                .addWarmupRunner(useCouponWarmupRunner());
        return registry;
    }

    @Bean
    public WarmupStarter warmupStarter() {
        return new WarmupStarter(warmupRegistry());
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("User-Id", userCouponWarmupProperties.getUserid().toString());
        return headers;
    }

}
