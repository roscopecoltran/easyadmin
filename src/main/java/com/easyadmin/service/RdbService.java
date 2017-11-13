package com.easyadmin.service;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Slf4j
@org.springframework.stereotype.Component
public class RdbService {
    /**
     * all the tenant cache the datasource
     */
    @Autowired
    Map<String, JdbcTemplate> jdbcTemplateMap;

    public String getCurrentSchema() {
        return Tenant.get().getCurrentDataSource().getMySqlDbName();
    }

    private HikariDataSource dataSource2Hikari(DataSource dataSource) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(dataSource.getJdbcUrl());
        ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        ds.setUsername(dataSource.getUsername());
        ds.setPassword(dataSource.getPassword());
        return ds;
    }

    public JdbcTemplate getJdbcTemplate() {
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        String jdbcUrl = dataSource.getJdbcUrl();
        if (!jdbcTemplateMap.containsKey(jdbcUrl)) {
            jdbcTemplateMap.put(jdbcUrl, new JdbcTemplate(dataSource2Hikari(dataSource)));
        }
        return jdbcTemplateMap.get(jdbcUrl);
    }

    public DbTable getDbTable(String tableName) {
        DbSpec spec = new DbSpec(getCurrentSchema());
        DbSchema dbSchema = new DbSchema(spec, getCurrentSchema());
        return new DbTable(dbSchema, tableName);
    }


}
