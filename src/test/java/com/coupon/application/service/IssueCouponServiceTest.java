package com.coupon.application.service;

import com.coupon.application.port.in.IssueCouponCommand;
import com.coupon.application.port.out.CouponStockRepository;
import com.coupon.application.port.out.CouponRepository;
import com.coupon.domain.core.Coupon;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.value.Status;
import com.coupon.domain.exception.DuplicateIssueException;
import com.coupon.domain.exception.NotFoundCouponStockException;
import com.coupon.domain.value.CouponId;
import com.coupon.domain.value.CouponStockId;
import com.coupon.domain.value.UserId;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class IssueCouponServiceTest {

    @Autowired
    private CouponStockRepository couponStockRepository;
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private IssueCouponService issueCouponService;

    @DisplayName("쿠폰 발급 요청시 쿠폰이 발급된다.")
    @Test
    public void issueCouponTest () {
        //given
        CouponStockId couponStockId = CouponStockId.of(1L);
        UserId userId = UserId.of(1L);
        IssueCouponCommand command = new IssueCouponCommand(couponStockId, userId);

        //when
        CouponId couponId = issueCouponService.issueCoupon(command);

        //then
        assertThat(couponId).isNotNull();

        CouponStock couponStock = couponStockRepository.
                findBy(couponStockId)
                .orElseThrow(() -> new NotFoundCouponStockException("테스트 실패"));
        assertThat(couponStock.getQuantity().getLeftQuantity()).isEqualTo(999);

        List<Coupon> coupons = couponRepository.findAllBy(userId);
        assertThat(coupons).hasSize(1)
                .extracting("userId","couponStockId","status")
                .containsExactly(Tuple.tuple(userId, couponStockId, Status.UNUSED));
     }

    @DisplayName("사용자는 같은 쿠폰을 중복해서 발급할 수 없다.")
    @Test
    public void issueDuplicateCouponTest () {
        //given
        CouponStockId couponStockId = CouponStockId.of(1L);
        UserId userId = UserId.of(1L);
        IssueCouponCommand command = new IssueCouponCommand(couponStockId, userId);

        //when //then
        issueCouponService.issueCoupon(command);
        assertThatThrownBy(() -> issueCouponService.issueCoupon(command))
                .isInstanceOf(DuplicateIssueException.class)
                .hasMessage("같은 쿠폰에 대해서 중복 발급할 수 없습니다.");
    }

    @DisplayName("존재하지 않는 쿠폰 재고에 대해서 요청할 수 없다.")
    @Test
    public void notFoundCouponStockTest () {
        //given
        CouponStockId couponStockId = CouponStockId.of(0L);
        UserId userId = UserId.of(1L);
        IssueCouponCommand command = new IssueCouponCommand(couponStockId, userId);
        
        //when //then
        assertThatThrownBy(() -> issueCouponService.issueCoupon(command))
                .isInstanceOf(NotFoundCouponStockException.class)
                .hasMessage("요청에 대한 쿠폰 재고가 없습니다.");
    }

}
