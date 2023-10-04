package com.ayuconpon.common;

import jakarta.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Long value) {
        return value == null ? null : Money.wons(value);
    }

}
