package com.ayucoupon.common.config.warmup;

import com.ayucoupon.common.aop.multidatasource.DataSourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public DataSourceAspect dataSourceAspect() {
        return new DataSourceAspect();
    }

}
