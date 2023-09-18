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
    LocalDateTime startedDate;
    @Column(name = "finished_at")
    LocalDateTime finishedDate;

    protected IssuePeriod(LocalDateTime startedDate, LocalDateTime finishedDate) {
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }

    public static IssuePeriod of(LocalDateTime startedDate, LocalDateTime finishedDate) {
        return new IssuePeriod(startedDate, finishedDate);
    }

}
