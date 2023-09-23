package com.ayuconpon.common;

import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@EqualsAndHashCode
public class Money {

    BigDecimal value;

    private Money(BigDecimal price) {
        if (isNegative(price)) throw new IllegalArgumentException("돈의 금액은 0 이상이어야 합니다.");
        this.value = price;
    }

    public static Money wons(long price) {
        return new Money(BigDecimal.valueOf(price));
    }

    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public Long getValue() {
        return value.longValue();
    }

}
