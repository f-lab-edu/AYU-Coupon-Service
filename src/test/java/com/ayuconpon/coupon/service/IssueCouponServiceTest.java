package com.ayuconpon.coupon.service;

import com.ayuconpon.exception.DuplicatedCouponException;
import com.ayuconpon.exception.NotFoundCouponException;
import com.ayuconpon.exception.RequireRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class IssueCouponServiceTest {

    @Autowired
    private IssueCouponService issueCouponService;


    @DisplayName("쿠폰 요청을 할 수 있다.")
    @Test
    public void  issueCoupon() {
        //given
        IssueCouponCommand command = new IssueCouponCommand(1L, 1L);

        //when
        Long issuedCouponId = issueCouponService.issue(command);

        //then
        assertThat(issuedCouponId).isNotNull();
     }

     @DisplayName("동일한 쿠폰은 중복 발급할 수 없다.")
     @Test
     public void duplicateIssueCoupon () {
         //given
         IssueCouponCommand command = new IssueCouponCommand(1L, 1L);
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
        IssueCouponCommand command = new IssueCouponCommand(0L, 1L);

        //when then
        assertThatThrownBy(() -> issueCouponService.issue(command))
                .isInstanceOf(RequireRegistrationException.class)
                .hasMessage("회원 가입이 필요한 사용자입니다.");
    }

    @DisplayName("발행된 쿠폰에 대해서만, 쿠폰 발급 요청할 수 있다.")
    @Test
    public void issueCouponForInvalidCoupon () {
        //given
        IssueCouponCommand command = new IssueCouponCommand(1L, 0L);

        //when then
        assertThatThrownBy(() -> issueCouponService.issue(command))
                .isInstanceOf(NotFoundCouponException.class)
                .hasMessage("발급 요청된 쿠폰이 존재하지 않습니다.");
    }

}
