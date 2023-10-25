package com.ayuconpon.usercoupon.controller;

import com.ayuconpon.common.Money;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.Quantity;
import com.ayuconpon.usercoupon.controller.request.UseUserCouponRequest;
import com.ayuconpon.usercoupon.controller.request.IssueUserCouponRequest;
import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import com.ayuconpon.usercoupon.service.*;
import com.ayuconpon.usercoupon.service.issue.IssueUserCouponCommand;
import com.ayuconpon.usercoupon.service.issue.IssueUserCouponFacade;
import com.ayuconpon.util.Coupons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = {UserCouponController.class})
@Import(UserCouponMapperImpl.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueUserCouponFacade issueUserCouponService;
    @MockBean
    private UseUserCouponService useUserCouponService;
    @MockBean
    private ShowUserCouponService showUserCouponService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;

    @DisplayName("쿠폰 발급을 요청한다.")
    @Test
    public void issueCoupon() throws Exception {
        // given
        Long couponId = 1L;
        Long userId = 1L;
        Long issuedUserCouponId = 1L;

        IssueUserCouponRequest request = new IssueUserCouponRequest(couponId);
        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, couponId);

        given(issueUserCouponService.issue(command)).willReturn(issuedUserCouponId);

        // when then
        mockMvc.perform(
                        post("/v1/users/me/user-coupons")
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
                        post("/v1/users/me/user-coupons")
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
                        post("/v1/users/me/user-coupons")
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
                        post("/v1/users/me/user-coupons")
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
    public void useCoupon() throws Exception {
        // given
        Long userId = 1L;
        Long userCouponId = 1L;
        Long productPrice = 10000L;
        Money discountedProductPrice = Money.wons(9000L);

        UseUserCouponRequest request = new UseUserCouponRequest(productPrice);
        UseUserCouponCommand command = new UseUserCouponCommand(userId, userCouponId, Money.wons(productPrice));

        given(useUserCouponService.use(command)).willReturn(discountedProductPrice);

        // when then
        mockMvc.perform(
                        patch("/v1/users/me/user-coupons/" + userCouponId)
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discountedProductPrice").value(String.valueOf(discountedProductPrice.getValue())));
    }

    @DisplayName("쿠폰 적용을 요청할 때는 사용자 아이디가 필요하다.")
    @Test
    public void useCouponWithoutUserId() throws Exception {
        // given
        Long userCouponId = 1L;
        Long productPrice = 10000L;

        UseUserCouponRequest request = new UseUserCouponRequest(productPrice);

        // when then
        mockMvc.perform(
                        patch("/v1/users/me/user-coupons/" + userCouponId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("인증되지 않은 사용자입니다."));
    }

    @DisplayName("쿠폰 적용을 요청할 때, 사용자 아이디는 숫자다")
    @Test
    public void useCouponWithInvalidFormatUserId() throws Exception {
        // given
        Long userCouponId = 1L;
        Long productPrice = 10000L;
        String userId = "invalid";

        UseUserCouponRequest request = new UseUserCouponRequest(productPrice);

        // when then
        mockMvc.perform(
                        patch("/v1/users/me/user-coupons/" + userCouponId)
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
    public void useCouponWithoutProductPrice() throws Exception {
        // given
        Long userId = 1L;
        Long userCouponId = 1L;
        Long productPrice = null;

        UseUserCouponRequest request = new UseUserCouponRequest(productPrice);

        // when then
        mockMvc.perform(
                        patch("/v1/users/me/user-coupons/" + userCouponId)
                                .header("User-Id", String.valueOf(userId))
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("상품 가격이 비어있습니다."));
    }

    @DisplayName("사용자 자신의 쿠폰 목록을 조회할 수 있다.")
    @Test
    public void showUserCoupons() throws Exception {
        //given
        List<UserCouponDto> userCouponDtos = getDefaultUserCouopnDtos();

        given(showUserCouponService.getUnexpiredUserCoupons(any(), any(), any())).willReturn(userCouponDtos);

        //when //then
        mockMvc.perform(
                        get("/v1/users/me/user-coupons")
                                .header("User-Id", String.valueOf(1L))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userCouponDtoList[0].userCouponId").value(userCouponDtos.get(0).getUserCouponId()))
                .andExpect(jsonPath("$.userCouponDtoList[0].couponName").value(userCouponDtos.get(0).getCouponName()))
                .andExpect(jsonPath("$.userCouponDtoList[0].discountType").value(userCouponDtos.get(0).getDiscountType().toString()))
                .andExpect(jsonPath("$.userCouponDtoList[0].discountContent").value(userCouponDtos.get(0).getDiscountContent()))
                .andExpect(jsonPath("$.userCouponDtoList[0].minProductPrice").value(userCouponDtos.get(0).getMinProductPrice()))
                .andExpect(jsonPath("$.userCouponDtoList[0].status").value(userCouponDtos.get(0).getStatus().toString()))
                .andExpect(jsonPath("$.userCouponDtoList[0].expiredAt").value(userCouponDtos.get(0).getExpiredAt()))

                .andExpect(jsonPath("$.userCouponDtoList[1].userCouponId").value(userCouponDtos.get(1).getUserCouponId()))
                .andExpect(jsonPath("$.userCouponDtoList[1].couponName").value(userCouponDtos.get(1).getCouponName()))
                .andExpect(jsonPath("$.userCouponDtoList[1].discountType").value(userCouponDtos.get(1).getDiscountType().toString()))
                .andExpect(jsonPath("$.userCouponDtoList[1].discountContent").value(userCouponDtos.get(1).getDiscountContent()))
                .andExpect(jsonPath("$.userCouponDtoList[1].minProductPrice").value(userCouponDtos.get(1).getMinProductPrice()))
                .andExpect(jsonPath("$.userCouponDtoList[1].status").value(userCouponDtos.get(1).getStatus().toString()))
                .andExpect(jsonPath("$.userCouponDtoList[1].expiredAt").value(userCouponDtos.get(1).getExpiredAt())
                );
    }

    private List<UserCouponDto> getDefaultUserCouopnDtos() {
        Long userId = 1L;
        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 24, 0, 0, 0);
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCouponWithQuantity(Quantity.of(0L));
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();
        UserCoupon fixUserCoupon = new UserCoupon(userId, fixDiscountCoupon, currentTime);
        UserCoupon rateUserCoupon = new UserCoupon(userId, rateDiscountCoupon, currentTime);

        return new ArrayList<>(
                List.of(userCouponMapper.toUserCouponDto(fixUserCoupon),
                        userCouponMapper.toUserCouponDto(rateUserCoupon))
        );

    }

}
