package com.easyadmin;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.DbColumnType;
import com.easyadmin.schema.enums.EsColumnType;
import com.mongodb.MongoClient;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.network.InetAddresses;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        fieldTypeMap.put(JDBCType.LONGVARCHAR, Component.Text);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, Component.Text);

        fieldTypeMap.put(JDBCType.DATE, Component.Date);
        fieldTypeMap.put(JDBCType.TIME, Component.Date);
        fieldTypeMap.put(JDBCType.TIMESTAMP, Component.Date);
        return fieldTypeMap;
    }

    @Bean
    public Map<EsColumnType, Component> esFieldTypeMap() {
        Map<EsColumnType, Component> esFieldTypeMap = new HashMap();
        esFieldTypeMap.put(EsColumnType._text, Component.Text);
        esFieldTypeMap.put(EsColumnType._keyword, Component.Text);

        esFieldTypeMap.put(EsColumnType._date, Component.Date);
        esFieldTypeMap.put(EsColumnType._boolean, Component.Boolean);

        esFieldTypeMap.put(EsColumnType._long, Component.Number);
        esFieldTypeMap.put(EsColumnType._integer, Component.Number);
        esFieldTypeMap.put(EsColumnType._short, Component.Number);
        esFieldTypeMap.put(EsColumnType._byte, Component.Number);
        esFieldTypeMap.put(EsColumnType._double, Component.Number);
        esFieldTypeMap.put(EsColumnType._float, Component.Number);
        esFieldTypeMap.put(EsColumnType._half_float, Component.Number);
        esFieldTypeMap.put(EsColumnType._scaled_float, Component.Number);

        return esFieldTypeMap;
    }

    @Bean
    public Map<Component, DbColumnType> componentStringMap() {
        Map<Component, DbColumnType> componentStringMap = new HashMap(3);
        componentStringMap.put(Component.Text, DbColumnType.varchar);
        componentStringMap.put(Component.Number, DbColumnType.number);
        componentStringMap.put(Component.Date, DbColumnType.datetime);
        return componentStringMap;
    }


    @Bean
    public Map<String, JdbcTemplate> jdbcTemplateMap(){
        return new HashMap<>();
    }

    @Bean
    public Map<String, MongoClient> uriMongoMap(){
        return new HashMap<>();
    }

    @Bean
    public Map<String,TransportClient> esClientMap(){
        return new HashMap<>();
    }
}
