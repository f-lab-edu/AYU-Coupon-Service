package com.ayuconpon.usercoupon.service;

import com.ayuconpon.common.Money;
import com.ayuconpon.common.exception.AlreadyUsedUserCouponException;
import com.ayuconpon.common.exception.NotFoundUserCouponException;
import com.ayuconpon.common.exception.RequireRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UseUserCouponServiceTest extends IssueCouponRepositorySupport {

    @Autowired
    private UseUserCouponService useUserCouponService;
    @Autowired
    private IssueUserCouponService issueUserCouponService;

    @DisplayName("쿠폰 적용 요청을 보낼 수 있다.")
    @Test
    public void applyUserCoupon() {
        //given
        Long userId = 1L;
        Long userCouponId = getDefaultUserCouponId(userId);
        Money productPrice = Money.wons(10000L);
        UseUserCouponCommand command = new UseUserCouponCommand(userId, userCouponId, productPrice);

        //when
        Money discountedProductPrice = useUserCouponService.use(command);

        //then
        assertThat(discountedProductPrice.getValue()).isEqualTo(9000L);
    }

    @DisplayName("가입된 사용자만, 쿠폰 적용 요청할 수 있다.")
    @Test
    public void applyUserCouponOfNonRegisteredUser() {
        //given
        Long invalidUserId = 0L;
        Long userCouponId = 1L;
        Money productPrice = Money.wons(10000L);
        UseUserCouponCommand command = new UseUserCouponCommand(invalidUserId, userCouponId, productPrice);

        //when then
        assertThatThrownBy(() -> useUserCouponService.use(command))
                .isInstanceOf(RequireRegistrationException.class)
                .hasMessage("회원 가입이 필요한 사용자입니다.");
    }

    @DisplayName("발급 받은 쿠폰만 사용할 수 있다.")
    @Test
    public void applyUnissuedUserCoupon() {
        //given
        Long anotherUserId = 2L;
        Long userId = 1L;
        Long userCouponId = getDefaultUserCouponId(anotherUserId);
        Money productPrice = Money.wons(10000L);
        UseUserCouponCommand command = new UseUserCouponCommand(userId, userCouponId, productPrice);


        //when then
        assertThatThrownBy(() -> useUserCouponService.use(command))
                .isInstanceOf(NotFoundUserCouponException.class)
                .hasMessage("요청한 쿠폰이 존재하지 않습니다.");
    }

    int successCount = 0;
    int failCount = 0;

    @DisplayName("사용된 쿠폰은 사용할 수 없다.")
    @Test
    public void applyAlreadyUsedUserCoupon() throws InterruptedException {
        //given
        Long userId = 1L;
        Long couponId = 1L;
        Long userCouponId = issueUserCouponService.issue(new IssueUserCouponCommand(userId, couponId));
        Money money = Money.wons(10000L);

        int numberOfRequest = 2;
        ExecutorService service = Executors.newFixedThreadPool(numberOfRequest);
        CountDownLatch latch = new CountDownLatch(numberOfRequest);

        //when
        UseUserCouponCommand command = new UseUserCouponCommand(userId, userCouponId, money);
        for (int i = 0; i < numberOfRequest; i++) {
            service.execute(() -> {
                try {
                    useUserCouponService.use(command);
                    successCount++;
                } catch (AlreadyUsedUserCouponException e) {
                    failCount++;
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        //then
        assertThat(successCount).isEqualTo(1);
        assertThat(failCount).isEqualTo(1);

    }

    private Long getDefaultUserCouponId(Long userId) {
        Long couponId = 1L;
        IssueUserCouponCommand command = new IssueUserCouponCommand(userId, couponId);
        return issueUserCouponService.issue(command);
    }

}
