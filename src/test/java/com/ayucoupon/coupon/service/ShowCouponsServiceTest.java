package com.ayucoupon.coupon.service;

import com.ayucoupon.common.Money;
import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.coupon.domain.value.DiscountPolicy;
import com.ayucoupon.coupon.domain.value.DiscountType;
import com.ayucoupon.coupon.domain.value.IssuePeriod;
import com.ayucoupon.coupon.domain.value.Quantity;
import com.ayucoupon.util.Coupons;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShowCouponsServiceTest {

    @Autowired
    private ShowCouponsService showCouponsService;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    public void beforeEach() {
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("전체 쿠폰 목록 조회를 요청할 수 있다.")
    @Test
    public void showCouponsService() {
        //given
        Coupon fixDiscountCoupon = Coupons.getDefaultFixDiscountCouponWithQuantity(Quantity.of(0L));
        Coupon rateDiscountCoupon = Coupons.getDefaultRateDiscountCoupon();
        Coupon finishedCoupon = getFinishedCoupon();
        couponRepository.saveAll(List.of(fixDiscountCoupon, rateDiscountCoupon, finishedCoupon));


        LocalDateTime currentTime = LocalDateTime.of(2023, 9, 23, 0, 0, 0);
        int pageNum = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);


        //when
        List<CouponDto> coupons = showCouponsService.getCouponsInProgress(currentTime, pageRequest);

        //then
        assertThat(coupons).hasSize(2)
                .extracting("id", "name", "discountType", "discountContent", "leftQuantity", "minProductPrice", "startedAt", "finishedAt", "status")
                .containsExactly(
                        tuple(
                                rateDiscountCoupon.getCouponId(),
                                rateDiscountCoupon.getName(),
                                rateDiscountCoupon.getDiscountPolicy().getDiscountType(),
                                rateDiscountCoupon.getDiscountPolicy().getDiscountRate().toString(),
                                rateDiscountCoupon.getQuantity().getLeftQuantity(),
                                rateDiscountCoupon.getMinProductPrice().getValue(),
                                rateDiscountCoupon.getIssuePeriod().getStartedAt().toString(),
                                rateDiscountCoupon.getIssuePeriod().getFinishedAt().toString(),
                                CouponMapper.IN_PROGRESS),
                        tuple(
                                fixDiscountCoupon.getCouponId(),
                                fixDiscountCoupon.getName(),
                                fixDiscountCoupon.getDiscountPolicy().getDiscountType(),
                                fixDiscountCoupon.getDiscountPolicy().getDiscountPrice().getValue().toString(),
                                fixDiscountCoupon.getQuantity().getLeftQuantity(),
                                fixDiscountCoupon.getMinProductPrice().getValue(),
                                fixDiscountCoupon.getIssuePeriod().getStartedAt().toString(),
                                fixDiscountCoupon.getIssuePeriod().getFinishedAt().toString(),
                                CouponMapper.FINISHED)
                );
    }


    public static Coupon getFinishedCoupon() {
        String name = "기본 쿠폰";
        DiscountPolicy discountPolicy = DiscountPolicy.of(
                DiscountType.RATE_DISCOUNT,
                new BigDecimal("0.1"),
                null);
        Quantity quantity = Quantity.of(100L);
        IssuePeriod issuePeriod = IssuePeriod.of(
                LocalDateTime.of(2023, 9, 21, 0, 0, 0),
                LocalDateTime.of(2023, 9, 22, 23, 59, 59));
        Money minProductPrice = Money.wons(5000L);
        Long usageHours = 72L;

        return new Coupon(
                name,
                discountPolicy,
                quantity,
                issuePeriod,
                minProductPrice,
                usageHours);
    }

}
