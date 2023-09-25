package com.ayuconpon.coupon.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class IssuePeriod {

    @Column(name = "started_at")
    LocalDateTime startedAt;
    @Column(name = "finished_at")
    LocalDateTime finishedAt;

    protected IssuePeriod(LocalDateTime startedAt, LocalDateTime finishedAt) {
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public static IssuePeriod of(LocalDateTime startedAt, LocalDateTime finishedAt) {
        return new IssuePeriod(startedAt, finishedAt);
    }

    public void validate(LocalDateTime currentTime) {
        if (currentTime.isBefore(startedAt) || currentTime.isAfter(finishedAt)) {
            throw new IllegalStateException("쿠폰 발급 기간이 아닙니다.");
        }
    }

}
