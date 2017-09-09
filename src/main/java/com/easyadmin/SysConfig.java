package com.easyadmin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
}
