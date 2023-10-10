package com.ayuconpon.usercoupon.controller;

import com.ayuconpon.common.Money;
import com.ayuconpon.usercoupon.controller.request.UseUserCouponRequest;
import com.ayuconpon.usercoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.usercoupon.controller.response.IssueUserCouponResponse;
import com.ayuconpon.usercoupon.controller.response.ApplyUserCouponResponse;
import com.ayuconpon.usercoupon.service.IssueUserCouponCommand;
import com.ayuconpon.usercoupon.service.IssueUserCouponService;
import com.ayuconpon.common.resolver.UserId;
import com.ayuconpon.usercoupon.service.UseUserCouponCommand;
import com.ayuconpon.usercoupon.service.UseUserCouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserCouponController {

    private final IssueUserCouponService issueUserCouponService;
    private final UseUserCouponService useUserCouponService;

    @PostMapping("/v1/users/me/user-coupons")
    public ResponseEntity<IssueUserCouponResponse> issueCoupon(
            @UserId Long userId,
            @Valid @RequestBody IssueUserCouponRequest issueUserCouponRequest) {

        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, issueUserCouponRequest.getCouponId());
        Long issuedUserCouponId = issueUserCouponService.issue(command);

        return ResponseEntity.ok(new IssueUserCouponResponse(issuedUserCouponId));
    }

    @PatchMapping("/v1/users/me/user-coupons/{userCouponId}")
    public ResponseEntity<ApplyUserCouponResponse> useCoupon(
            @UserId Long userid,
            @PathVariable Long userCouponId,
            @Valid @RequestBody UseUserCouponRequest useUserCouponRequest) {

        UseUserCouponCommand command = new UseUserCouponCommand(userid,
                userCouponId,
                Money.wons(useUserCouponRequest.getProductPrice()));
        Money discountedProductPrice = useUserCouponService.use(command);

        return ResponseEntity.ok(new ApplyUserCouponResponse(discountedProductPrice.getValue()));
    }

}
