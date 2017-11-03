package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.data.RequestScope;
import com.easyadmin.schema.domain.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.Table;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Slf4j
@Service("dataDbService")
public class DataRdbServiceImpl implements DataService {
    @Autowired
    RdbService rdbService;

    @Autowired
    SchemaService schemaService;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        Map<String, Object> collect = allRequestParams.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        RequestScope requestScope = new ObjectMapper().convertValue(collect, RequestScope.class);

        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addAllColumns()
                .addFromTable(table);

        buildSelectQuery(selectQuery, entity, allRequestParams);

        sort(selectQuery, entity, requestScope);


        List<Map<String, Object>> dbDataList = rdbService.getJdbcTemplate().queryForList(selectQuery.toString() + pagination(requestScope));

        List<Map<String, Object>> inputDataList =
                dbDataList.stream()
                        .map(d -> dbMap2InputMap(entity, d))
                        .collect(Collectors.toList());
        return inputDataList;
    }

    private String pagination(RequestScope requestScope) {
        return " limit " + requestScope.get_start() + "," + requestScope.getLimit();
    }

    private void buildSelectQuery(SelectQuery selectQuery, String entity, Map<String, Object> allRequestParams) {
        allRequestParams.entrySet()
                .stream()
                .filter(map ->
                        (!map.getKey().equals(Constants.Q) && !map.getKey().startsWith("_"))
                )
                .forEach(objectEntry -> {
                    try {
                        buildQuery(selectQuery, entity, objectEntry, schemaService.findFields(entity));
                    } catch (Exception e) {
                        log.error("build rdb query error!", e);
                    }
                });
    }

    private void buildQuery(SelectQuery selectQuery, String entity, Map.Entry<String, Object> entry, List<Field> fields) throws Exception {
        log.info("entry:{}", entry);
        Map<String, Field> result =
                fields.stream().collect(Collectors.toMap(Field::getId,
                        Function.identity()));
        Field field = result.get(entry.getKey());
        DbColumn dbColumn = rdbService.getDbColumn(entity, field.getName());
        switch (field.getComponent()) {
            case Boolean:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            case NullableBoolean:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            case CheckboxGroup:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            case Number:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            case ReferenceArray:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            case SelectArray:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;
            default:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, entry.getValue()));
                break;

        }

    }

    @Override
    public long count(String entity, Map<String, Object> allRequestParams) {
        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addCustomColumns(FunctionCall.countAll())
                .addFromTable(table);

        buildSelectQuery(selectQuery, entity, allRequestParams);
        return rdbService.getJdbcTemplate().queryForObject(selectQuery.toString(), Long.class);
    }

    /**
     * sort
     *
     * @param requestScope
     * @return
     */
    private void sort(SelectQuery selectQuery, String entity, RequestScope requestScope) {
        OrderObject.Dir dir = "DESC".equalsIgnoreCase(requestScope.get_order()) ? OrderObject.Dir.DESCENDING : OrderObject.Dir.ASCENDING;
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        try {
            selectQuery.addOrdering(fieldMap.containsKey(requestScope.get_sort()) ? rdbService.getDbColumn(entity, fieldMap.get(requestScope.get_sort()).getName()) : rdbService.getIdColumn(entity), dir);
        } catch (Exception e) {
            log.error("add sort error", e);
        }
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        DbTable table = rdbService.getDbTable(entity);
        DbColumn idColumn = rdbService.getIdColumn(entity);
        String query1 =
                new SelectQuery()
                        .addAllTableColumns(table)
                        .addCondition(BinaryCondition.equalTo(idColumn, id))
                        .validate().toString();
        System.out.println(query1);
        Map<String, Object> dbMap = rdbService.getJdbcTemplate().queryForMap(query1);
        return dbMap2InputMap(entity, dbMap);
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        Map<String, Object> dbMap = inputMap2dbMap(entity, data);
        Table table = rdbService.getDbTable(entity);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);
        dbMap.entrySet()
                .stream()
                .filter(a -> !"id".equals(a.getKey())).forEach(objectEntry -> {
            try {
                insertCustomerQuery.addColumn(rdbService.getDbColumn(entity, objectEntry.getKey()), objectEntry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println(insertCustomerQuery);
        rdbService.getJdbcTemplate().execute(insertCustomerQuery.toString());
        return data;
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        Map<String, Object> dbMap = inputMap2dbMap(entity, data);
        DbTable table = rdbService.getDbTable(entity);
        DbColumn idColumn = rdbService.getIdColumn(entity);
        UpdateQuery updateQuery = new UpdateQuery(table);
        dbMap.entrySet()
                .stream().filter(a -> !"id".equals(a.getKey()))
                .forEach(objectEntry -> {
                    try {
                        updateQuery.addSetClause(rdbService.getDbColumn(entity, objectEntry.getKey()), objectEntry.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        updateQuery.addCondition(BinaryCondition.equalTo(idColumn, id));
        rdbService.getJdbcTemplate().execute(updateQuery.toString());
        return data;
    }

    @Override
    public void delete(String entity, String id) {
        DbTable table = rdbService.getDbTable(entity);
        DbColumn idColumn = rdbService.getIdColumn(entity);
        DeleteQuery deleteQuery = new DeleteQuery(table);
        deleteQuery.addCondition(BinaryCondition.equalTo(idColumn, id));
        rdbService.getJdbcTemplate().execute(deleteQuery.toString());
    }

    public Map<String, Object> inputMap2dbMap(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        return data.entrySet().stream().collect(Collectors.toMap(p -> fieldMap.containsKey(p.getKey()) ? fieldMap.get(p.getKey()).getName() : p.getKey(), p -> p.getValue()));
    }

    public Map<String, Object> dbMap2InputMap(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = getFieldNameMap(entity);
        return data.entrySet().stream().collect(Collectors.toMap(p -> fieldMap.containsKey(p.getKey()) ? fieldMap.get(p.getKey()).getId() : p.getKey(), p -> p.getValue()));
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
