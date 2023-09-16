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
public class UserId implements Serializable{

    private Long id;

    protected UserId(Long id) {
        this.id = id;
    }

    public static UserId of(Long id) {
        return new UserId(id);
    }

}
