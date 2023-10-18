package com.ayuconpon.usercoupon.controller.response;

import com.ayuconpon.usercoupon.service.UserCouponDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowUserCouponsResponse {

     private List<UserCouponDto> userCouponDtoList;

     public static ShowUserCouponsResponse from(List<UserCouponDto> userCouponDtos) {
         ShowUserCouponsResponse showUserCouponsResponse = new ShowUserCouponsResponse();
         showUserCouponsResponse.userCouponDtoList = userCouponDtos;
         return showUserCouponsResponse;
     }

}
