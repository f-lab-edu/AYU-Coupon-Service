package com.ayuconpon.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceConfigProperties.class)
@RequiredArgsConstructor
public class DataSourceConfig {

    private final DataSourceConfigProperties properties;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getDriverClassName());
        hikariConfig.setJdbcUrl(properties.getUrl());
        hikariConfig.setUsername(properties.getUserName());
        hikariConfig.setPassword(properties.getPassword());
        hikariConfig.setMaximumPoolSize(properties.getMaximumPoolSize());
        return new HikariDataSource(hikariConfig);
    }

}
