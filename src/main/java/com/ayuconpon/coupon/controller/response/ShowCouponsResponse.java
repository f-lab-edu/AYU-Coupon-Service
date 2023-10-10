package com.ayuconpon.coupon.controller.response;

import com.ayuconpon.coupon.service.CouponDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowCouponsResponse {

    private List<CouponDto> couponDtoList;

    public static ShowCouponsResponse from(List<CouponDto> couponDtos) {
        ShowCouponsResponse couponDtoList = new ShowCouponsResponse();
        couponDtoList.couponDtoList = couponDtos;
        return couponDtoList;
    }

}
