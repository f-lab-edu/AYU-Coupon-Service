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
public class CouponId implements Serializable {

    private Long id;

    protected CouponId(Long id) {
        this.id = id;
    }

    public static CouponId of(Long id) {
        return new CouponId(id);
    }

}
