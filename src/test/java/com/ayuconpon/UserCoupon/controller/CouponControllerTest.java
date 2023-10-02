package com.ayuconpon.UserCoupon.controller;

import com.ayuconpon.userCoupon.controller.UserCouponController;
import com.ayuconpon.userCoupon.controller.request.ApplyUserCouponRequest;
import com.ayuconpon.userCoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.userCoupon.service.IssueUserCouponCommand;
import com.ayuconpon.userCoupon.service.IssueUserCouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {UserCouponController.class})
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueUserCouponService issueCouponService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("쿠폰 발급을 요청한다.")
    @Test
    public void issueCoupon() throws Exception {
        // given
        Long couponId = 1L;
        Long userId = 1L;
        Long issuedUserCouponId = 1L;

        IssueUserCouponRequest request = new IssueUserCouponRequest(couponId);
        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, couponId);

        given(issueCouponService.issue(command)).willReturn(issuedUserCouponId);

        // when then
        mockMvc.perform(
                        post("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issuedUserCouponId").value(String.valueOf(issuedUserCouponId)));
    }

    @DisplayName("쿠폰 발급 요청할 때는, 유저 아이디가 필요하다.")
    @Test
    public void issueCouponWithoutUserId() throws Exception {
        // given
        Long couponId = 1L;

        IssueUserCouponRequest request = new IssueUserCouponRequest(couponId);


        // when then
        mockMvc.perform(
                        post("/users/coupons")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("인증되지 않은 사용자입니다."));
    }

    @DisplayName("쿠폰 발급 요청할 때, 요청 유저 아이디는 숫자다.")
    @Test
    public void issueCouponWithInvalidFormatUserId() throws Exception {
        // given
        Long couponId = 1L;
        String userId = "invalid";

        IssueUserCouponRequest request = new IssueUserCouponRequest(couponId);

        // when then
        mockMvc.perform(
                        post("/users/coupons")
                                .content(objectMapper.writeValueAsString(request))
                                .header("User-Id", String.valueOf(userId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("잘못된 형식의 사용자 아이디입니다."));
    }

    @DisplayName("쿠폰 발급 요청할 때는, 쿠폰 규칙 아이디가 필요하다.")
    @Test
    public void issueCouponWithoutCouponStockId() throws Exception {
        // given
        Long couponId = null;
        Long userId = 1L;

        IssueUserCouponRequest request = new IssueUserCouponRequest(couponId);

        // when then
        mockMvc.perform(
                        post("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("쿠폰 발급 요청 아이디가 비어있습니다."));
    }

    @DisplayName("쿠폰 적용을 요청한다.")
    @Test
    public void applyCoupon() throws Exception {
        // given
        Long userId = 1L;
        Long userCouponId = 1L;
        Long productPrice = 10000L;
        Long discountedProductPrice = 9000L;

        ApplyUserCouponRequest request = new ApplyUserCouponRequest(userCouponId, productPrice);

        // when then
        mockMvc.perform(
                        patch("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discountedProductPrice").value(String.valueOf(discountedProductPrice)));
    }

    @DisplayName("쿠폰 적용을 요청할 때는, 발급된 쿠폰 아이디가 필요하다.")
    @Test
    public void applyCouponWithoutCouponStockId() throws Exception {
        // given
        Long userId = 1L;
        Long userCouponId = null;
        Long productPrice = 10000L;

        ApplyUserCouponRequest request = new ApplyUserCouponRequest(userCouponId, productPrice);

        // when then
        mockMvc.perform(
                        patch("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("쿠폰 아이디가 비어있습니다."));
    }

    @DisplayName("쿠폰 적용을 요청할 때는 사용자 아이디가 필요하다.")
    @Test
    public void applyCouponWithoutUserId() throws Exception {
        // given
        Long userCouponId = 1L;
        Long productPrice = 10000L;

        ApplyUserCouponRequest request = new ApplyUserCouponRequest(userCouponId, productPrice);

        // when then
        mockMvc.perform(
                        patch("/users/coupons")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("인증되지 않은 사용자입니다."));
    }

    @DisplayName("쿠폰 적용을 요청할 때, 사용자 아이디는 숫자다")
    @Test
    public void applyCouponWithInvalidFormatUserId() throws Exception {
        // given
        Long userCouponId = 1L;
        Long productPrice = 10000L;
        String userId = "invalid";

        ApplyUserCouponRequest request = new ApplyUserCouponRequest(userCouponId, productPrice);

        // when then
        mockMvc.perform(
                        patch("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("잘못된 형식의 사용자 아이디입니다."));
    }

    @DisplayName("쿠폰 발급 요청할 때는, 상품 가격이 필요하다.")
    @Test
    public void applyCouponWithoutProductPrice() throws Exception {
        // given
        Long userId = 1L;
        Long userCouponId = 1L;
        Long productPrice = null;

        ApplyUserCouponRequest request = new ApplyUserCouponRequest(userCouponId, productPrice);

        // when then
        mockMvc.perform(
                        patch("/users/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("상품 가격이 비어있습니다."));
    }

}
