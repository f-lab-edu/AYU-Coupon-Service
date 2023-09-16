package com.coupon.application.port.out;

import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.exception.NotFoundCouponStockException;
import com.coupon.domain.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@Transactional
@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponStockRepository couponStockRepository;
    @Autowired
    private CouponRuleRepository couponRuleRepository;


    @DisplayName("유저 아이디로 유저가 발급받은 쿠폰을 조회한다.")
    @Test
    public void findAllByUserId () {
        //given
        UserId userId = UserId.of(1L);

        Coupon coupon1 = makeDefaultCouponWithUserId(userId);
        Coupon coupon2 = makeDefaultCouponWithUserId(userId);
        Coupon coupon3 = makeDefaultCouponWithUserId(userId);
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        couponRepository.save(coupon3);

        //when
        List<Coupon> coupons = couponRepository.findAllBy(userId);

        //then
        assertThat(coupons)
                .hasSize(3)
                .extracting("couponId", "userId", "couponStockId","couponRule.id", "expireDate", "status" )
                .containsExactlyInAnyOrder(
                        tuple(coupon1.getCouponId(), coupon1.getUserId(), coupon1.getCouponStockId(), coupon1.getCouponRule().getId(), coupon1.getExpireDate(), coupon1.getStatus()),
                        tuple(coupon2.getCouponId(), coupon2.getUserId(), coupon2.getCouponStockId(), coupon2.getCouponRule().getId(), coupon2.getExpireDate(), coupon2.getStatus()),
                        tuple(coupon3.getCouponId(), coupon3.getUserId(), coupon3.getCouponStockId(), coupon3.getCouponRule().getId(), coupon3.getExpireDate(), coupon3.getStatus())
                );
     }

    @DisplayName("발급 받은 쿠폰이 없을 때, 크기가 0인 쿠폰 리스트를 반환한다.")
    @Test
    public void findAllEmpty() {

        //when
        List<Coupon> coupons = couponRepository.findAllBy(UserId.of(-1L));

        assertThat(coupons)
                .isNotNull()
                .hasSize(0);
    }

    private Coupon makeDefaultCouponWithUserId(UserId userId) {
        CouponStock couponStock = couponStockRepository.findBy(CouponStockId.of(1L))
                .orElseThrow(() -> new NotFoundCouponStockException("쿠폰 재고 찾기 실패"));

        LocalDateTime requestIssueDate = LocalDateTime.of(2023, Month.OCTOBER, 14, 0, 0);

        return couponStock.issueCoupon(userId, requestIssueDate);
    }

}
