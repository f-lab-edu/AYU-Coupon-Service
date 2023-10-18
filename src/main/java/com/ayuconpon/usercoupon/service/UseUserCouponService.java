package com.ayuconpon.usercoupon.service;

import com.ayuconpon.common.Money;
import com.ayuconpon.common.exception.NotFoundUserCouponException;
import com.ayuconpon.common.exception.RequireRegistrationException;
import com.ayuconpon.user.domain.UserRepository;
import com.ayuconpon.usercoupon.domain.UserCouponRepository;
import com.ayuconpon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UseUserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;

    @Transactional
    public Money use(UseUserCouponCommand command) {
        validateRegisteredUser(command);
        return applyUserCoupon(command);
    }

    private Money applyUserCoupon(UseUserCouponCommand command) {
        UserCoupon userCoupon = userCouponRepository.findByIdWithPessimisticLock(command.userCouponId(), command.userId())
                .orElseThrow(NotFoundUserCouponException::new);

        LocalDateTime currentTime = LocalDateTime.now();
        return userCoupon.use(command.productPrice(), currentTime);
    }

    private void validateRegisteredUser(UseUserCouponCommand command) {
        boolean isExist = userRepository.existsById(command.userId());
        if (!isExist) throw new RequireRegistrationException();
    }

}
