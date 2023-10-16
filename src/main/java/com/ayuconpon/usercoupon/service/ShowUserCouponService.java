package com.ayuconpon.usercoupon.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowUserCouponService {

    public List<UserCouponDto> getUserCouponsInProgress(Long userId, Pageable pageable) {
        return null;
    }

}
