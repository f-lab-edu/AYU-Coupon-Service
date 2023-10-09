package com.ayuconpon.coupon.controller;

import com.ayuconpon.coupon.service.CouponDto;
import com.ayuconpon.coupon.controller.response.ShowCouponsResponse;
import com.ayuconpon.coupon.service.ShowCouponsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final ShowCouponsService showCouonsService;

    @GetMapping("/coupons")
    public ResponseEntity<ShowCouponsResponse> showCoupons() {
        List<CouponDto> couponDtos = showCouonsService.getCoupons();
        ShowCouponsResponse showCouponsResponse = ShowCouponsResponse.from(couponDtos);
        return ResponseEntity.ok(showCouponsResponse);
    }

}
