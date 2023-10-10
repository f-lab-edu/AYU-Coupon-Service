package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.CouponRepository;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.coupon.domain.value.Quantity;
import com.ayuconpon.util.Coupons;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
        couponRepository.saveAll(List.of(fixDiscountCoupon, rateDiscountCoupon));

        int pageNum = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        //when
        List<CouponDto> coupons = showCouponsService.getCoupons(pageRequest);

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

}
