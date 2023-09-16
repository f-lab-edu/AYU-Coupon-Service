package com.coupon.domain.exception;

public class OutOfCouponStockException extends RuntimeException {
    public OutOfCouponStockException(String message) {super(message);}
}
