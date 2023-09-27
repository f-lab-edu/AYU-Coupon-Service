package com.ayuconpon.coupon.controller;
import com.ayuconpon.common.ApiResponse;
import com.ayuconpon.coupon.controller.request.IssueCouponRequest;
import com.ayuconpon.coupon.controller.response.IssueCouponResponse;
import com.ayuconpon.resolver.UserId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    @PostMapping("/api/coupons")
    public ApiResponse<IssueCouponResponse> issueCoupon(
            @UserId Long userId,
            @Valid @RequestBody IssueCouponRequest issueCouponRequest) {

        // TODO 쿠폰 서비스 개발

        return ApiResponse.ok(new IssueCouponResponse(1L));
    }

}
