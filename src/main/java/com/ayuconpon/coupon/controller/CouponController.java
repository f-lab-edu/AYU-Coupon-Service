package com.ayuconpon.coupon.controller;
import com.ayuconpon.coupon.controller.request.IssueCouponRequest;
import com.ayuconpon.coupon.controller.response.IssueCouponResponse;
import com.ayuconpon.coupon.service.IssueCouponCommand;
import com.ayuconpon.coupon.service.IssueCouponService;
import com.ayuconpon.resolver.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final IssueCouponService issueCouponService;

    @PostMapping("/users/coupons")
    public ResponseEntity<IssueCouponResponse> issueCoupon(
            @UserId Long userId,
            @Valid @RequestBody IssueCouponRequest issueCouponRequest) {

        IssueCouponCommand command = new IssueCouponCommand(userId, issueCouponRequest.getCouponId());
        Long issuedCouponId = issueCouponService.issue(command);

        return ResponseEntity.ok(new IssueCouponResponse(issuedCouponId));
    }

}
