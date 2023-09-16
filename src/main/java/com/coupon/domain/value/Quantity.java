package com.coupon.domain.value;

import com.coupon.domain.exception.OutOfCouponStockException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quantity {

    public static Quantity ZERO = Quantity.of(0L);

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "left_quantity")
    private Long leftQuantity;

    protected Quantity(Long quantity) {
        this.totalQuantity = quantity;
        this.leftQuantity = quantity;
    }

    public static Quantity of(Long value) {
        return new Quantity(value);
    }

    public Quantity minus(final Quantity provided) {
        if (leftQuantity - provided.leftQuantity < 0) {
            throw  new OutOfCouponStockException("쿠폰 재고가 없습니다.");
        }
        return Quantity.of(leftQuantity - provided.leftQuantity);
    }

}
