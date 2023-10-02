package com.ayuconpon.coupon.controller;

import com.ayuconpon.coupon.controller.request.IssueCouponRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {CouponController.class})
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("쿠폰 발급을 요청한다.")
    @Test
    public void issueCoupon() throws Exception {
        // given
        Long couponRuleId = 1L;
        Long userId = 1L;
        Long issuedCouponId = 1L;

        IssueCouponRequest request = new IssueCouponRequest(couponRuleId);

        // when then
        mockMvc.perform(
                        post("/api/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issuedCouponId").value(String.valueOf(issuedCouponId)));
    }

    @DisplayName("쿠폰 발급 요청할 때는, 유저 아이디가 필요하다.")
    @Test
    public void issueCouponWithoutUserId() throws Exception {
        // given
        Long couponRuleId = 1L;

        IssueCouponRequest request = new IssueCouponRequest(couponRuleId);

        // when then
        mockMvc.perform(
                        post("/api/coupons")
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
        Long couponRuleId = 1L;
        String userId = "invalid";

        IssueCouponRequest request = new IssueCouponRequest(couponRuleId);

        // when then
        mockMvc.perform(
                        post("/api/coupons")
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
        Long couponRuleId = null;
        Long userId = 1L;

        IssueCouponRequest request = new IssueCouponRequest(couponRuleId);

        // when then
        mockMvc.perform(
                        post("/api/coupons")
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("쿠폰 발급 요청 아이디가 비어있습니다."));
    }

}
