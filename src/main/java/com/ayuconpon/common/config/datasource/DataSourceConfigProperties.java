package com.ayuconpon.common.config.datasource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("my.master.datasource")
public class DataSourceConfigProperties {

    private final String driverClassName;
    private final String url;
    private final String userName;
    private final String password;
    private final Integer maximumPoolSize;

}
