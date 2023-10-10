package com.ayuconpon.coupon.controller;

import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.Quantity;
import com.ayuconpon.coupon.service.CouponDto;
import com.ayuconpon.coupon.service.CouponMapper;
import com.ayuconpon.coupon.service.CouponMapperImpl;
import com.ayuconpon.coupon.service.ShowCouponsService;
import com.ayuconpon.util.Coupons;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CouponController.class})
@Import(CouponMapperImpl.class)
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShowCouponsService showCouponsService;
    @Autowired
    private CouponMapper couponMapper;

    @DisplayName("발행된 전체 쿠폰 목록을 조회할 수 있다.")
    @Test
    public void showCoupons() throws Exception {
        //given
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCouponWithQuantity(Quantity.of(0L));
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();
        List<CouponDto> couponDtos = new ArrayList<>();
        couponDtos.add(couponMapper.toCouponDto(fixDiscountCoupon));
        couponDtos.add(couponMapper.toCouponDto(rateDiscountCoupon));

        given(showCouponsService.getCoupons(any())).willReturn(couponDtos);

        //when //then
        mockMvc.perform(
                        get("/v1/coupons")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.couponDtoList[0].id").value(fixDiscountCoupon.getCouponId()))
                .andExpect(jsonPath("$.couponDtoList[0].name").value(fixDiscountCoupon.getName()))
                .andExpect(jsonPath("$.couponDtoList[0].discountType").value(fixDiscountCoupon.getDiscountPolicy().getDiscountType().toString()))
                .andExpect(jsonPath("$.couponDtoList[0].discountContent").value(fixDiscountCoupon.getDiscountPolicy().getDiscountPrice().getValue().toString()))
                .andExpect(jsonPath("$.couponDtoList[0].leftQuantity").value(fixDiscountCoupon.getQuantity().getLeftQuantity()))
                .andExpect(jsonPath("$.couponDtoList[0].minProductPrice").value(fixDiscountCoupon.getMinProductPrice().getValue()))
                .andExpect(jsonPath("$.couponDtoList[0].startedAt").value(fixDiscountCoupon.getIssuePeriod().getStartedAt().toString()))
                .andExpect(jsonPath("$.couponDtoList[0].finishedAt").value(fixDiscountCoupon.getIssuePeriod().getFinishedAt().toString()))
                .andExpect(jsonPath("$.couponDtoList[0].status").value(CouponMapper.FINISHED))
                .andExpect(jsonPath("$.couponDtoList[1].id").value(rateDiscountCoupon.getCouponId()))
                .andExpect(jsonPath("$.couponDtoList[1].name").value(rateDiscountCoupon.getName()))
                .andExpect(jsonPath("$.couponDtoList[1].discountType").value(rateDiscountCoupon.getDiscountPolicy().getDiscountType().toString()))
                .andExpect(jsonPath("$.couponDtoList[1].discountContent").value(rateDiscountCoupon.getDiscountPolicy().getDiscountRate().toString()))
                .andExpect(jsonPath("$.couponDtoList[1].leftQuantity").value(rateDiscountCoupon.getQuantity().getLeftQuantity()))
                .andExpect(jsonPath("$.couponDtoList[1].minProductPrice").value(rateDiscountCoupon.getMinProductPrice().getValue()))
                .andExpect(jsonPath("$.couponDtoList[1].startedAt").value(rateDiscountCoupon.getIssuePeriod().getStartedAt().toString()))
                .andExpect(jsonPath("$.couponDtoList[1].finishedAt").value(rateDiscountCoupon.getIssuePeriod().getFinishedAt().toString()))
                .andExpect(jsonPath("$.couponDtoList[1].status").value(CouponMapper.IN_PROGRESS));

    }

}
