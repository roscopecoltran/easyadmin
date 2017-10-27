package com.easyadmin;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@Component
@ConfigurationProperties(prefix="easyadmin")
public class SysConfig {
    private boolean checkforapply;

    public boolean isCheckforapply() {
        return checkforapply;
    }

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}
