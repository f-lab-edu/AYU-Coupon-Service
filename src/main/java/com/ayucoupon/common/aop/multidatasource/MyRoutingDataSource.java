package com.ayucoupon.common.aop.multidatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceNameContextHolder.getDataSourceName();
    }

}
