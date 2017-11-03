package com.easyadmin.service;

import com.easyadmin.schema.domain.Field;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Service("dataDbService")
public class DataRdbServiceImpl implements DataService {
    @Autowired
    RdbService rdbService;

    @Autowired
    SchemaService schemaService;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {

        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String,Object>> dbDataList=jdbcTemplate.queryForList("select * from " + entity);
        List<Map<String,Object>> inputDataList =
                dbDataList.stream()
                        .map(d->dbMap2InputMap(entity,d))
                        .collect(Collectors.toList());
        return inputDataList;
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
        Map<String,Object> dbMap=jdbcTemplate.queryForMap(query1);
        return dbMap2InputMap(entity,dbMap);
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        Map<String, Object> dbMap = inputMap2dbMap(entity, data);
        DataSource dataSource = rdbService.getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String schema = rdbService.getSchema();
        Table table = rdbService.getDbTable(schema, entity);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);
        dbMap.entrySet()
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
        Map<String, Object> dbMap = inputMap2dbMap(entity, data);
        String schema = rdbService.getSchema();
        DbTable table = rdbService.getDbTable(schema, entity);
        DbColumn idColumn = new DbColumn(table, "id", "int");
        UpdateQuery updateQuery = new UpdateQuery(table);
        dbMap.entrySet()
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

    public Map<String, Object> inputMap2dbMap(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        return data.entrySet().stream().collect(Collectors.toMap(p -> fieldMap.containsKey(p.getKey())?fieldMap.get(p.getKey()).getName():p.getKey(), p -> p.getValue()));
    }

    public Map<String, Object> dbMap2InputMap(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = getFieldNameMap(entity);
        return data.entrySet().stream().collect(Collectors.toMap(p -> fieldMap.containsKey(p.getKey())?fieldMap.get(p.getKey()).getId():p.getKey(), p -> p.getValue()));
    }

    public Map<String, Field> getFieldNameMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().collect(Collectors.toMap(Field::getName,
                Function.identity()));
    }

    public Map<String, Field> getFieldIdMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().collect(Collectors.toMap(Field::getId,
                Function.identity()));
    }

}
