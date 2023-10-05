package com.ayuconpon.usercoupon.controller;

import com.ayuconpon.usercoupon.controller.request.ApplyUserCouponRequest;
import com.ayuconpon.usercoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.usercoupon.controller.response.IssueUserCouponResponse;
import com.ayuconpon.usercoupon.controller.response.ApplyUserCouponResponse;
import com.ayuconpon.usercoupon.service.IssueUserCouponCommand;
import com.ayuconpon.usercoupon.service.IssueUserCouponService;
import com.ayuconpon.common.resolver.UserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserCouponController {

    private final IssueUserCouponService issueUserCouponService;

    @PostMapping("/users/coupons")
    public ResponseEntity<IssueUserCouponResponse> issueCoupon(
            @UserId Long userId,
            @Valid @RequestBody IssueUserCouponRequest issueUserCouponRequest) {

        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, issueUserCouponRequest.getCouponId());
        Long issuedUserCouponId = issueUserCouponService.issue(command);

        return ResponseEntity.ok(new IssueUserCouponResponse(issuedUserCouponId));
    }

    @PatchMapping("/users/coupons")
    public ResponseEntity<ApplyUserCouponResponse> applyCoupon(
            @UserId Long userid,
            @Valid @RequestBody ApplyUserCouponRequest applyUserCouponRequest) {

        // todo 쿠폰 적용 서비스 구현

        return ResponseEntity.ok(new ApplyUserCouponResponse(9000L));
    }

}
