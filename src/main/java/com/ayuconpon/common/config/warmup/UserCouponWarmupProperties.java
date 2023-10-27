package com.ayuconpon.common.config.warmup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("my.warmup.user-coupon")
public class UserCouponWarmupProperties {

    private final Integer warmupCount;
    private final Long userid;
    private final Long useApiPathSegment;
    private final String baseUrl;
    private final String useApiBody;
    private final String issueApiBody;

}
