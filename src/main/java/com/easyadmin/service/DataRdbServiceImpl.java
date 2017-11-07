package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.data.RequestScope;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmarketscience.sqlbuilder.*;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    @Autowired
    Map<Component, String> componentStringMap;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> allRequestParams) {
        /**
         * 过滤出系统字段
         */
        Map<String, Object> collect = allRequestParams.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        RequestScope requestScope = new ObjectMapper().convertValue(collect, RequestScope.class);

        /**
         * 构建rdb select query
         */
        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addAllColumns()
                .addFromTable(table);
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        buildSelectQuery(selectQuery, entity, allRequestParams, fieldMap);

        /**
         * 排序
         */
        sort(selectQuery, entity, requestScope, fieldMap);

        String sql = selectQuery.toString() + pagination(requestScope);
        log.info("list record ,entity:{},data:{},sql:{}", entity, allRequestParams, sql);

        /**
         * 查询结果
         */
        List<Map<String, Object>> dbDataList = rdbService.getJdbcTemplate().queryForList(sql);

        /**
         * field id wrapper
         */
        List<Map<String, Object>> inputDataList =
                dbDataList.stream()
                        .map(d -> addIdValue(entity, d))
                        .collect(Collectors.toList());
        return inputDataList;
    }

    /**
     * 分页
     *
     * @param requestScope
     * @return
     */
    private String pagination(RequestScope requestScope) {
        return " limit " + requestScope.get_start() + "," + requestScope.getLimit();
    }

    private void buildSelectQuery(SelectQuery selectQuery, String entity, Map<String, Object> allRequestParams, Map<String, Field> fieldMap) {

        allRequestParams.entrySet()
                .stream()
                .filter(map ->
                        (!map.getKey().equals(Constants.Q) && !map.getKey().startsWith("_"))
                )
                .forEach(request -> {
                    buildQuery(selectQuery, entity, request, fieldMap.get(request.getKey()));
                });
    }

    /**
     * 构建select query
     *
     * @param selectQuery
     * @param entity
     * @param entry
     * @param field
     */
    private void buildQuery(SelectQuery selectQuery, String entity, Map.Entry<String, Object> entry, Field field) {
        log.info("entry:{}", entry);
        DbTable table = rdbService.getDbTable(entity);
        DbColumn dbColumn = getDbColumn(table, field);
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
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addCustomColumns(FunctionCall.countAll())
                .addFromTable(table);

        buildSelectQuery(selectQuery, entity, allRequestParams, fieldMap);
        log.info("count record ,entity:{},data:{},sql:{}", entity, allRequestParams, selectQuery);
        return rdbService.getJdbcTemplate().queryForObject(selectQuery.toString(), Long.class);
    }

    /**
     * sort
     *
     * @param requestScope
     * @return
     */
    private void sort(SelectQuery selectQuery, String entity, RequestScope requestScope, Map<String, Field> fieldMap) {
        OrderObject.Dir dir = "DESC".equalsIgnoreCase(requestScope.get_order()) ? OrderObject.Dir.DESCENDING : OrderObject.Dir.ASCENDING;
        DbTable table = rdbService.getDbTable(entity);
        if (fieldMap.containsKey(requestScope.get_sort())) {
            selectQuery.addOrdering(getDbColumn(table, fieldMap.get(requestScope.get_sort())), dir);
        }
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        Map<String, Field> primaryFieldIdMap = getPrimaryFieldIdMap(entity);
        DbTable table = rdbService.getDbTable(entity);
        String[] idValues = id.split(Constants.delimiter);
        SelectQuery selectQuery =
                new SelectQuery()
                        .addAllTableColumns(table);
        int idx = 0;
        primaryFieldIdMap.forEach((k, v) -> {
            selectQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, v), idValues[idx]));
        });
        log.info("findOne record , entity:{},id:{},sql:{}", entity, id, selectQuery);
        Map<String, Object> dbMap = rdbService.getJdbcTemplate().queryForMap(selectQuery.toString());
        return dbMap;
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        Map<String, Field> fieldIdMap = getFieldIdMap(entity);
        wrapData(fieldIdMap, data);
        DbTable table = rdbService.getDbTable(entity);
        InsertQuery insertCustomerQuery =
                new InsertQuery(table);
        data.entrySet()
                .stream()
                .filter(a -> !Constants.id.equals(a.getKey())).forEach(k -> {
            insertCustomerQuery.addColumn(getDbColumn(table, fieldIdMap.get(k.getKey())), k.getValue());
        });

        log.info("save record ,entity:{},id:{},sql:{}", entity, data, insertCustomerQuery);
        rdbService.getJdbcTemplate().execute(insertCustomerQuery.toString());
        return data;
    }

    private void wrapData(Map<String, Field> fieldIdMap, Map<String, Object> data) {
        data.entrySet().removeIf(entry -> !fieldIdMap.containsKey(entry.getKey()) || fieldIdMap.get(entry.getKey()).getIsAutoIncremented());
        data.forEach((k, v) -> {
            Field field = fieldIdMap.get(k);
            switch (field.getComponent()) {
                case Date:
                    DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    DateTime dateTime = f.parseDateTime(v.toString());
                    data.put(k, dateTime == null ? null : JdbcEscape.timestamp(dateTime.toDate()));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        Map<String, Field> fieldIdMap = getFieldIdMap(entity);
        wrapData(fieldIdMap, data);
        DbTable table = rdbService.getDbTable(entity);
        UpdateQuery updateQuery = new UpdateQuery(table);
        data.entrySet()
                .stream().filter(a -> !Constants.id.equals(a.getKey()))
                .forEach(k -> {
                    updateQuery.addSetClause(getDbColumn(table, fieldIdMap.get(k.getKey())), k.getValue());
                });
        fieldIdMap.forEach((k, v) -> {
            if (v.getIsPartOfPrimaryKey()) {
                updateQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, v), data.get(v.getName())));
            }
        });
        log.info("update record , entity:{},data:{},sql:{}", entity, data, updateQuery);
        rdbService.getJdbcTemplate().execute(updateQuery.toString());
        return data;
    }

    @Override
    public void delete(String entity, String id) {
        Map<String, Field> primaryFieldIdMap = getPrimaryFieldIdMap(entity);
        DbTable table = rdbService.getDbTable(entity);
        String[] idValues = id.split(Constants.delimiter);
        DeleteQuery deleteQuery = new DeleteQuery(table);
        int idx = 0;
        primaryFieldIdMap.forEach((k, v) -> {
            deleteQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, primaryFieldIdMap.get(k)), idValues[idx]));
        });
        log.info("delete record , entity:{},data:{},sql:{}", entity, id, deleteQuery);
        rdbService.getJdbcTemplate().execute(deleteQuery.toString());
    }

    public Map<String, Object> addIdValue(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = getFieldIdMap(entity);
        String idValue = fieldMap.entrySet().stream().filter(field -> field.getValue().getIsPartOfPrimaryKey())
                .map(field -> data.get(field.getValue().getName()).toString())
                .collect(Collectors.joining(Constants.delimiter));
        data.put(Constants.id, idValue);
        return data;
    }

    public Map<String, Field> getFieldIdMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().collect(Collectors.toMap(Field::getName, Function.identity(),
                (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                },
                TreeMap::new));
    }

    public Map<String, Field> getPrimaryFieldIdMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().filter(field -> field.getIsPartOfPrimaryKey()).collect(Collectors.toMap(Field::getName, Function.identity(),
                (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                },
                TreeMap::new));
    }

    private DbColumn getDbColumn(DbTable table, Field field) {
        return new DbColumn(table, field.getName(), componentStringMap.get(field.getComponent()));
    }
}
