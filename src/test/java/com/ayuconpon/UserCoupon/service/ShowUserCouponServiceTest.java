package com.ayuconpon.usercoupon.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ShowUserCouponServiceTest {

    @Autowired
    private ShowUserCouponService showUserCouponService;
    @Autowired
    private IssueUserCouponService issueUserCouponService;

    @DisplayName("사용자의 쿠폰 목록을 조회할 수 있다.")
    @Test
    public void showCouponServiceTest() {
        //given
        Long userId = 1L;
        Long otherUserId = 2L;
        IssueUserCouponCommand issueUserCouponCommandA = new IssueUserCouponCommand(userId, 1L);
        IssueUserCouponCommand issueUserCouponCommandB = new IssueUserCouponCommand(userId, 2L);
        IssueUserCouponCommand issueUserCouponCommandC = new IssueUserCouponCommand(otherUserId, 2L);

        issueUserCouponService.issue(issueUserCouponCommandA);
        issueUserCouponService.issue(issueUserCouponCommandB);
        issueUserCouponService.issue(issueUserCouponCommandC);

        LocalDateTime currentTime = LocalDateTime.now();

        int pageNum = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        //when
        List<UserCouponDto> userCoupons = showUserCouponService.getUnexpiredUserCoupons(userId, currentTime, pageRequest);

        //then
        assertThat(userCoupons).hasSize(2);
    }

    @DisplayName("사용 기간이 끝난 사용자 쿠폰은 조회하지 않는다.")
    @Test
    public void showUnexpiredCouponServiceTest() {
        //given
        Long userId = 1L;
        IssueUserCouponCommand issueUserCouponCommandA = new IssueUserCouponCommand(userId, 1L);
        issueUserCouponService.issue(issueUserCouponCommandA);

        LocalDateTime expiredTime = LocalDateTime.now().plusDays(1).plusSeconds(1);

        int pageNum = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        //when
        List<UserCouponDto> userCoupons = showUserCouponService.getUnexpiredUserCoupons(userId, expiredTime, pageRequest);

        //then
        assertThat(userCoupons).hasSize(0);
    }

}
