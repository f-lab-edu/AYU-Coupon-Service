package com.ayuconpon.user.domain.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("활동 상태"),
    DORMANCY("휴면 상태");

    private final String status;

}
