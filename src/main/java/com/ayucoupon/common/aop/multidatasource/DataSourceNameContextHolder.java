package com.ayucoupon.common.aop.multidatasource;

import org.springframework.util.Assert;

public class DataSourceNameContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceName(String dataSourceName) {
        Assert.hasText(dataSourceName, "DataSource name must has text");
        contextHolder.set(dataSourceName);
    }

    public static String getDataSourceName() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

}
