package com.ayuconpon.coupon.service;

import com.ayuconpon.common.Money;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.DiscountPolicy;
import com.ayuconpon.coupon.domain.value.DiscountType;
import com.ayuconpon.coupon.domain.value.Quantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    String IN_PROGRESS = "진행중";
    String FINISHED = "진행마감";

    @Mapping(source = "couponId", target = "id")
    @Mapping(source = "quantity.leftQuantity", target = "leftQuantity")
    @Mapping(source = "issuePeriod.startedAt", target = "startedAt", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "issuePeriod.finishedAt", target = "finishedAt", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "minProductPrice", target = "minProductPrice", qualifiedByName = "minProductPrice")
    @Mapping(source = "discountPolicy", target = "discountType", qualifiedByName = "discountType")
    @Mapping(source = "discountPolicy", target = "discountContent", qualifiedByName = "discountContent")
    @Mapping(source = "quantity.leftQuantity", target = "status", qualifiedByName = "status")
    CouponDto toCouponDto(Coupon coupon);

    @Named("localDateTimeToString")
    static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }

    @Named("minProductPrice")
    static Long minProductPrice(Money minProductPrice) {
        return minProductPrice.getValue();
    }

    @Named("discountType")
    static DiscountType discountType(DiscountPolicy discountPolicy) {
        return discountPolicy.getDiscountType();
    }

    @Named("discountContent")
    static String discountContent(DiscountPolicy discountPolicy) {
        if (discountPolicy.getDiscountType() == DiscountType.FIX_DISCOUNT) {
            return discountPolicy.getDiscountPrice().getValue().toString();
        }
        return discountPolicy.getDiscountRate().toString();
    }

    @Named("status")
    static String status(Long leftQuantity) {
        if (leftQuantity > 0) {
            return IN_PROGRESS;
        }
        return FINISHED;
    }

}
