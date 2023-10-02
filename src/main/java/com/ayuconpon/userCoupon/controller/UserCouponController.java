package com.ayuconpon.userCoupon.controller;
import com.ayuconpon.userCoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.userCoupon.controller.response.IssueUserCouponResponse;
import com.ayuconpon.userCoupon.service.IssueUserCouponCommand;
import com.ayuconpon.userCoupon.service.IssueUserCouponService;
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

}
