package com.ayuconpon.coupon.domain.value;

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

    public void decrease() {
        if (leftQuantity <= 0) throw new IllegalStateException("쿠폰의 재고가 없습니다.");
        leftQuantity--;
    }

}
