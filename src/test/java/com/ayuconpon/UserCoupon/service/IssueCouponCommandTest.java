package com.ayuconpon.usercoupon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
class IssueCouponCommandTest {

    @DisplayName("쿠폰 발급 명령을 만들 수 있다.")
    @Test
    public void createIssueCouponCommand() throws Exception {
        // given
        Long userId = 1L;
        Long couponId = 2L;

        // when
        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, couponId);

        // then
        assertThat(command).isNotNull()
                .extracting("userId", "couponId")
                .containsExactly(userId, couponId);
    }

    @DisplayName("쿠폰 발급 명령을 만들때는 유저 아이디가 필요하다.")
    @Test
    public void createIssueCouponCommandWithoutUserId() throws Exception {
        // given
        Long userId = null;
        Long couponId = 2L;

        // when then
        assertThatThrownBy(() -> new IssueUserCouponCommand(userId, couponId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("user id가 비어있습니다.");
    }

    @DisplayName("쿠폰 발급 명령을 만들때는 쿠폰 규칙 아이디가 필요하다.")
    @Test
    public void createIssueCouponCommandWithoutCouponId() throws Exception {
        // given
        Long userId = 1L;
        Long couponId = null;

        // when then
        assertThatThrownBy(() -> new IssueUserCouponCommand(userId, couponId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("coupon id가 비어있습니다.");
    }

  
}
