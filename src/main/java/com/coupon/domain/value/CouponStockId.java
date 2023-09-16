package com.coupon.domain.value;

import jakarta.persistence.Column;
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
public class CouponStockId implements Serializable {

    @Column(name = "id")
    private Long id;

    protected CouponStockId(Long id) {
        this.id = id;
    }

    public static CouponStockId of(Long id) {
        return new CouponStockId(id);
    }

}
