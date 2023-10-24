package com.ayuconpon.common.config.warmup;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("my.warmup.user-coupon")
public class UserCouponWarmupProperties {

    private final Integer warmupCount;
    private final Long dummyUserId;
    private final Long dummyIssueCouponId;
    private final String url;
    private final String useUserCouponApiBody;
    private final String issueUserCouponApiBody;

}
