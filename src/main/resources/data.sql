insert into coupon(coupon_id, name, discount_type, discount_rate, discount_price, total_quantity, left_quantity, started_at, finished_at, usage_hours, min_product_price)
values (1, 'event fix coupon A','FIX_DISCOUNT', null, 1000, 1000, 1000,'2023-09-14', '2023-10-14', 24, 10000),
       (2, 'event fix coupon B','FIX_DISCOUNT', null, 1000, 1000, 1000,'2023-09-14', '2023-10-14', 24, 10000),
       (3, 'event rate coupon C','RATE_DISCOUNT', 0.1, null, 1000, 1000,'2023-09-14', '2023-10-14', 24, 10000),
       (4, 'event rate coupon D','RATE_DISCOUNT', 0.1, null, 1000, 1000,'2023-09-14', '2023-10-14', 24, 10000);


insert into user(user_id, name, status)
values (1, 'userA', 'ACTIVE'),
       (2, 'userB', 'ACTIVE'),
       (3, 'userC', 'ACTIVE'),
       (4, 'userD', 'ACTIVE'),
       (5, 'userE', 'ACTIVE'),
       (6, 'userF', 'DORMANCY'),
       (7, 'userG', 'DORMANCY');

