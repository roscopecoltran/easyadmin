package com.easyadmin.service;

import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.Table;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gongxinyi
 * @date 2017-11-02
 */
@Service("dataDbService")
public class DataRdbServiceImpl implements DataService {
    @Autowired
    RdbService rdbService;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForList("select * from " + entity);
    }

    @Override
    public long count(String entity, Map<String, Object> allRequestParams) {
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.queryForObject("select count(*) from " + entity, Long.class);
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String schema = rdbService.getSchema();
        DbTable table = rdbService.getDbTable(schema, entity);
        DbColumn idColumn = new DbColumn(table, "id", "int");
        String query1 =
                new SelectQuery()
                        .addAllTableColumns(table)
                        .addCondition(BinaryCondition.equalTo(idColumn, id))
                        .validate().toString();
        System.out.println(query1);
        return jdbcTemplate.queryForMap(query1);
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String schema = rdbService.getSchema();
        Table table = rdbService.getDbTable(schema, entity);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);
        data.entrySet()
                .stream()
                .filter(a -> !"id".equals(a.getKey())).forEach(objectEntry -> {
            try {
                insertCustomerQuery.addColumn(rdbService.getDbColumn(schema, entity, objectEntry.getKey()), objectEntry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println(insertCustomerQuery);
        jdbcTemplate.execute(insertCustomerQuery.toString());
        return data;
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        String schema = rdbService.getSchema();
        DbTable table = rdbService.getDbTable(schema, entity);
        DbColumn idColumn = new DbColumn(table, "id", "int");
        UpdateQuery updateQuery = new UpdateQuery(table);
        data.entrySet()
                .stream().filter(a -> !"id".equals(a.getKey()))
                .forEach(objectEntry -> {
                    try {
                        updateQuery.addSetClause(rdbService.getDbColumn(schema, entity, objectEntry.getKey()), objectEntry.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        updateQuery.addCondition(BinaryCondition.equalTo(idColumn, id));
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(updateQuery.toString());
        return data;
    }

    @Override
    public void delete(String entity, String id) {
        String schema = rdbService.getSchema();
        DbTable table = rdbService.getDbTable(schema, entity);
        DbColumn idColumn = new DbColumn(table, "id", "int");
        DeleteQuery deleteQuery = new DeleteQuery(table);
        deleteQuery.addCondition(BinaryCondition.equalTo(idColumn, id));
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(deleteQuery.toString());
    }
}
