package com.ayuconpon.coupon.service;

import com.ayuconpon.coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowCouponsService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    private static final String COUPON_ID = "couponId";

    public List<CouponDto> getCouponsInProgress(LocalDateTime currentTime, Pageable pageable) {
        Pageable pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, COUPON_ID));

        return couponRepository.findCouponsInProgress(currentTime, pageRequest)
                .stream()
                .map(couponMapper::toCouponDto)
                .toList();
    }

}
