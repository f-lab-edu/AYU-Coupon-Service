package com.ayucoupon.usercoupon.service;

import com.ayucoupon.common.aop.multidatasource.DataSource;
import com.ayucoupon.coupon.domain.CouponRepository;
import com.ayucoupon.coupon.domain.entity.Coupon;
import com.ayucoupon.usercoupon.domain.UserCouponRepository;
import com.ayucoupon.usercoupon.domain.entity.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowUserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserCouponMapper userCouponMapper;

    private static final String USER_COUPON_ID = "userCouponId";

    @DataSource("primary")
    public List<UserCouponDto> getUnexpiredUserCoupons(Long userId, LocalDateTime currentTime, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, USER_COUPON_ID));

        Map<Long, UserCoupon> userCouponMap = userCouponRepository.getUnexpiredUserCoupons(userId, currentTime, pageRequest)
                .stream()
                .collect(Collectors.toMap(
                        UserCoupon::getCouponId,
                        userCoupon -> userCoupon
                ));

        return couponRepository.findAllById(userCouponMap.keySet())
                .stream()
                .map(coupon -> {
                    UserCoupon userCoupon = userCouponMap.get(coupon.getCouponId());
                    return userCouponMapper.toUserCouponDto(userCoupon, coupon);
                })
                .toList();
    }

}
