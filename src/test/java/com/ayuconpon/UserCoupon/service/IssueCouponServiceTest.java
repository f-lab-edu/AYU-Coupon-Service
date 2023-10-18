package com.ayuconpon.usercoupon.service;

import com.ayuconpon.coupon.domain.CouponRepository;
import com.ayuconpon.usercoupon.domain.UserCouponRepository;
import com.ayuconpon.coupon.domain.entity.Coupon;
import com.ayuconpon.common.exception.DuplicatedCouponException;
import com.ayuconpon.common.exception.NotFoundCouponException;
import com.ayuconpon.common.exception.RequireRegistrationException;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@Transactional
class IssueCouponServiceTest extends IssueCouponRepositorySupport {

    @Autowired
    private IssueUserCouponService issueCouponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserCouponRepository userCouponRepository;
    
    @AfterEach
    public void afterEach() {
        userCouponRepository.deleteAllInBatch();
    }

    @DisplayName("쿠폰 요청을 할 수 있다.")
    @Test
    public void  issueCoupon() {
        //given
        IssueUserCouponCommand command = new IssueUserCouponCommand(1L, 1L);

        //when
        Long issuedCouponId = issueCouponService.issue(command);

        //then
        assertThat(issuedCouponId).isNotNull();
     }

     @DisplayName("동일한 쿠폰은 중복 발급할 수 없다.")
     @Test
     public void duplicateIssueCoupon () {
         //given
         IssueUserCouponCommand command = new IssueUserCouponCommand(1L, 1L);
         issueCouponService.issue(command);

         //when then
         assertThatThrownBy(() -> issueCouponService.issue(command))
                 .isInstanceOf(DuplicatedCouponException.class)
                 .hasMessage("쿠폰이 이미 발급되었습니다.");
      }

    @DisplayName("가입된 사용자만, 쿠폰 발급 요청할 수 있다.")
    @Test
    public void issueCouponOfNonRegisteredUser () {
        //given
        IssueUserCouponCommand command = new IssueUserCouponCommand(0L, 1L);

        //when then
        assertThatThrownBy(() -> issueCouponService.issue(command))
                .isInstanceOf(RequireRegistrationException.class)
                .hasMessage("회원 가입이 필요한 사용자입니다.");
    }

    @DisplayName("발행된 쿠폰에 대해서만, 쿠폰 발급 요청할 수 있다.")
    @Test
    public void issueCouponForInvalidCoupon () {
        //given
        IssueUserCouponCommand command = new IssueUserCouponCommand(1L, 0L);

        //when then
        assertThatThrownBy(() -> issueCouponService.issue(command))
                .isInstanceOf(NotFoundCouponException.class)
                .hasMessage("발급 요청된 쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("쿠폰을 발급하면, 쿠폰 재고가 감소한다.")
    @Test
    public void pessimisticLockIssueCouponTest () throws InterruptedException {
        //given
        Long couponId = 1L;
        Long userNum = 5L;

        int couponLeftQuantity = 5;
        int numberOfThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(couponLeftQuantity);

        //when
        for (int userId = 1; userId <= userNum; userId++) {
            IssueUserCouponCommand command = new IssueUserCouponCommand((long) userId, couponId);
            service.execute(() -> {
                issueCouponService.issue(command);
                latch.countDown();
            });
        }
        latch.await();

        // then
        assertThat(userCouponRepository.count()).isEqualTo(couponLeftQuantity);

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(RuntimeException::new);
        assertThat(coupon.getQuantity().getLeftQuantity()).isEqualTo(0);
    }

}
