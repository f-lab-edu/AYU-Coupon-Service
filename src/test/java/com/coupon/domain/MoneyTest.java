package com.coupon.domain;

import com.coupon.domain.value.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @DisplayName("금액으로 돈을 생성한다.")
    @Test
    public void  createPositiveOrZeroMoney() {
        //given
        long price = 0;

        //when
        Money money = Money.wons(price);

        //then
        assertThat(money).isNotNull();
        assertThat(money.getValue()).isEqualTo(price);
     }

    @DisplayName("음수의 돈을 만들 때 예외를 던진다.")
    @Test
    public void  createNegativeMoney() {
        //given
        long price = -1;

        //when //then
        assertThatThrownBy(() -> Money.wons(price))
                .isInstanceOf(Exception.class)
                .hasMessage("돈의 금액은 0 이상이어야 합니다.");
    }

}
