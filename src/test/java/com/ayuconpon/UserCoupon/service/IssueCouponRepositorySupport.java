package com.ayuconpon.usercoupon.service;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public abstract class IssueCouponRepositorySupport {

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    public void beforeEach() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        String sql = "update coupon set left_quantity=5  where coupon_id=1";
        connection.prepareStatement(sql).executeUpdate();

        sql = "delete from user_coupon;";
        connection.prepareStatement(sql).executeUpdate();

        connection.commit();
        connection.close();
    }

}
