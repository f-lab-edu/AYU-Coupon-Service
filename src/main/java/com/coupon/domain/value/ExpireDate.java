package com.coupon.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ExpireDate {

    @Column(name = "expired_at")
    LocalDateTime expireDate;

    protected ExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public static ExpireDate of(LocalDateTime expireDate) {
        return new ExpireDate(expireDate);
    }

    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(expireDate);
    }

}
