package com.ayucoupon.usercoupon.service;

import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowUserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final UserCouponMapper userCouponMapper;

    private static final String USER_COUPON_ID = "userCouponId";

    public List<UserCouponDto> getUnexpiredUserCoupons(Long userId, LocalDateTime currentTime, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, USER_COUPON_ID));

        return userCouponRepository.getUnexpiredUserCoupons(userId, currentTime, pageRequest)
                .stream()
                .map(userCouponMapper::toUserCouponDto)
                .toList();
    }

}
