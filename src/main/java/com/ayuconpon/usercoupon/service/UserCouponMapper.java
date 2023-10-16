package com.ayuconpon.usercoupon.service;

import com.ayuconpon.common.Money;
import com.ayuconpon.coupon.domain.value.DiscountPolicy;
import com.ayuconpon.coupon.domain.value.DiscountType;
import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserCouponMapper {

    UserCouponMapper INSTANCE = Mappers.getMapper(UserCouponMapper.class);

    @Mapping(source = "coupon.name", target = "couponName")
    @Mapping(source = "coupon.discountPolicy", target = "discountType", qualifiedByName = "discountType")
    @Mapping(source = "coupon.discountPolicy", target = "discountContent", qualifiedByName = "discountContent")
    @Mapping(source = "coupon.minProductPrice", target = "minProductPrice", qualifiedByName = "minProductPrice")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "expiredAt", target = "expiredAt", qualifiedByName = "localDateTimeToString")
    UserCouponDto toUserCouponDto(UserCoupon userCoupon);

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

}
