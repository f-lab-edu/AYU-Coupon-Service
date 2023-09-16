package com.coupon.domain.value;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponRuleId implements Serializable {

    private Long id;

    protected CouponRuleId(Long id) {
        this.id = id;
    }

    public static CouponRuleId of(Long id) {
        return new CouponRuleId(id);
    }

}

