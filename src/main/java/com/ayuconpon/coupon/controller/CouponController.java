package com.ayuconpon.coupon.controller;

import com.ayuconpon.coupon.service.CouponDto;
import com.ayuconpon.coupon.controller.response.ShowCouponsResponse;
import com.ayuconpon.coupon.service.ShowCouponsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final ShowCouponsService showCouponsService;

    @GetMapping("/v1/coupons")
    public ResponseEntity<ShowCouponsResponse> showCoupons(Pageable pageable) {
        List<CouponDto> couponDtos = showCouponsService.getCoupons(pageable);
        ShowCouponsResponse showCouponsResponse = ShowCouponsResponse.from(couponDtos);
        return ResponseEntity.ok(showCouponsResponse);
    }

}
