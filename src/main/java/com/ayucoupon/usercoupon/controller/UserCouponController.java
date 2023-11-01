package com.ayucoupon.usercoupon.controller;

import com.ayucoupon.common.Money;
import com.ayucoupon.usercoupon.controller.request.UseUserCouponRequest;
import com.ayucoupon.usercoupon.controller.request.IssueUserCouponRequest;
import com.ayucoupon.usercoupon.controller.response.IssueUserCouponResponse;
import com.ayucoupon.usercoupon.controller.response.ShowUserCouponsResponse;
import com.ayucoupon.usercoupon.controller.response.UseUserCouponResponse;
import com.ayucoupon.usercoupon.service.*;

import com.ayucoupon.usercoupon.service.issue.IssueUserCouponCommand;
import com.ayucoupon.usercoupon.service.issue.IssueUserCouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserCouponController {

    private final IssueUserCouponService issueUserCouponService;
    private final UseUserCouponService useUserCouponService;
    private final ShowUserCouponService showUserCouponService;

    @GetMapping("/v1/users/me/user-coupons")
    public ResponseEntity<ShowUserCouponsResponse> showUserCoupons(@RequestAttribute Long userId, Pageable pageable) {

        List<UserCouponDto> userCouponDtos = showUserCouponService.getUnexpiredUserCoupons(userId, LocalDateTime.now(), pageable);
        ShowUserCouponsResponse response = ShowUserCouponsResponse.from(userCouponDtos);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1/users/me/user-coupons")
    public ResponseEntity<IssueUserCouponResponse> issueCoupon(
            @RequestAttribute Long userId,
            @Valid @RequestBody IssueUserCouponRequest issueUserCouponRequest) {

        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, issueUserCouponRequest.getCouponId());
        Long issuedUserCouponId = issueUserCouponService.issue(command);

        return ResponseEntity.ok(new IssueUserCouponResponse(issuedUserCouponId));
    }

    @PatchMapping("/v1/users/me/user-coupons/{userCouponId}")
    public ResponseEntity<UseUserCouponResponse> useCoupon(
            @RequestAttribute Long userId,
            @PathVariable Long userCouponId,
            @Valid @RequestBody UseUserCouponRequest useUserCouponRequest) {

        UseUserCouponCommand command = new UseUserCouponCommand(userId,
                userCouponId,
                Money.wons(useUserCouponRequest.getProductPrice()));
        Money discountedProductPrice = useUserCouponService.use(command);

        return ResponseEntity.ok(new UseUserCouponResponse(discountedProductPrice.getValue()));
    }

}
