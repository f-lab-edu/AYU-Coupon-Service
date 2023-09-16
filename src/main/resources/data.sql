insert into coupon_rule
values (1, 'event coupon','FIX_DISCOUNT', null, 1000,'2023-09-14', '2023-10-14', 24, 10000),
       (2, 'surprise coupon', 'RATE_DISCOUNT', 0.1, null, '2023-09-14', '2023-10-14', 24, 5000);

insert into coupon_stock
values (1, 1, 1000, 1000),
       (2, 2, 1000, 1000);
