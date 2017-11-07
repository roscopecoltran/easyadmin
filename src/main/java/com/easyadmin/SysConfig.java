package com.easyadmin;

import com.easyadmin.schema.enums.Component;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@org.springframework.stereotype.Component
@ConfigurationProperties(prefix = "easyadmin")
public class SysConfig {
    private boolean checkforapply;

    public boolean isCheckforapply() {
        return checkforapply;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Map<JDBCType, Component> fieldTypeMap() {
        Map<JDBCType, Component> fieldTypeMap = new HashMap<JDBCType, Component>();
        fieldTypeMap.put(JDBCType.CHAR, Component.Text);
        fieldTypeMap.put(JDBCType.VARCHAR, Component.Text);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, Component.Text);

        fieldTypeMap.put(JDBCType.NUMERIC, Component.Number);
        fieldTypeMap.put(JDBCType.DECIMAL, Component.Number);

        fieldTypeMap.put(JDBCType.BIT, Component.Boolean);

        fieldTypeMap.put(JDBCType.TINYINT, Component.Number);
        fieldTypeMap.put(JDBCType.SMALLINT, Component.Number);

        fieldTypeMap.put(JDBCType.INTEGER, Component.Number);
        fieldTypeMap.put(JDBCType.BIGINT, Component.Number);
        fieldTypeMap.put(JDBCType.REAL, Component.Number);
        fieldTypeMap.put(JDBCType.FLOAT, Component.Number);
        fieldTypeMap.put(JDBCType.DOUBLE, Component.Number);

        fieldTypeMap.put(JDBCType.BINARY, Component.Text);
        fieldTypeMap.put(JDBCType.VARBINARY, Component.Text);
        fieldTypeMap.put(JDBCType.LONGVARBINARY, Component.Text);

        fieldTypeMap.put(JDBCType.DATE, Component.Date);
        fieldTypeMap.put(JDBCType.TIME, Component.Date);
        fieldTypeMap.put(JDBCType.TIMESTAMP, Component.Date);
        return fieldTypeMap;
    }

    @Bean
    public Map<Component, String> componentStringMap() {
        Map<Component, String> componentStringMap = new HashMap<Component, String>();
        componentStringMap.put(Component.Text, "varchar");
        componentStringMap.put(Component.Number, "number");
        componentStringMap.put(Component.Date, "datetime");
        return componentStringMap;
    }
}
