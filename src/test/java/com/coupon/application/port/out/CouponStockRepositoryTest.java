package com.coupon.application.port.out;

import com.coupon.domain.core.CouponRule;
import com.coupon.domain.core.CouponStock;
import com.coupon.domain.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class CouponStockRepositoryTest {

    @Autowired
    private CouponStockRepository couponStockRepository;

    @Autowired
    private CouponRuleRepository couponRuleRepository;

    @DisplayName("쿠폰 재고 아이디로 쿠폰 재고를 조회한다.")
    @Test
    public void findCouponStockById () {
        //given
        Quantity quantity = Quantity.of(100L);
        CouponStock defaultCouponStock = getDefaultCouponStockWithQuantity(quantity);
        couponStockRepository.save(defaultCouponStock);

        CouponStockId couponStockId = defaultCouponStock.getId();

        //when
        Optional<CouponStock> couponStockOpt = couponStockRepository.findBy(couponStockId);

        //then
        assertThat(couponStockOpt.isPresent()).isTrue();
        assertThat(couponStockOpt.get())
                .extracting("id", "couponRule.id", "quantity.leftQuantity")
                .containsExactly(defaultCouponStock.getId(), defaultCouponStock.getCouponRule().getId(), quantity.getLeftQuantity());
     }

     private CouponStock getDefaultCouponStockWithQuantity(Quantity quantity) {
         CouponRule couponRule = couponRuleRepository.findBy(CouponRuleId.of(1L))
                 .orElseThrow(NoSuchElementException::new);

         return CouponStock.newInstance(
                 couponRule,
                 quantity);
     }

}
