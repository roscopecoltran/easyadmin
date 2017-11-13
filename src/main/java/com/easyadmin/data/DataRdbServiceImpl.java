package com.easyadmin.data;

import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.domain.Filter;
import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.DbColumnType;
import com.easyadmin.schema.enums.DbTypeEnum;
import com.easyadmin.service.RdbService;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Slf4j
@Service
public class DataRdbServiceImpl implements IDataService {
    @Autowired
    RdbService rdbService;

    @Autowired
    DataServiceHelper dataServiceHelper;

    @Autowired
    Map<Component, DbColumnType> componentStringMap;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> filters) {
        /**
         * 过滤出系统字段
         */
        RequestScope requestScope = getRequestScope(filters);

        /**
         * 构建rdb select query
         */
        Map<String, Object> fieldFilters = getFieldFilter(filters);
        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addAllColumns()
                .addFromTable(table);
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);

        Object[] args = new Object[fieldFilters.size() + 2];

        buildSelectQuery(selectQuery, args, entity, fieldFilters, fieldMap);

        /**
         * 排序
         */
        sort(selectQuery, entity, requestScope, fieldMap);


        String sql = selectQuery.toString() + pagination(requestScope);
        args[args.length - 1] = requestScope.getLimit();
        args[args.length - 2] = requestScope.get_start();
        log.info("list record ,entity:{},data:{},sql:{}", entity, filters, sql);

        /**
         * 查询结果
         */
        List<Map<String, Object>> dbDataList = rdbService.getJdbcTemplate().queryForList(sql, args);

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
     * 过滤系统字段
     *
     * @param filters
     * @return
     */
    private RequestScope getRequestScope(Map<String, Object> filters) {
        Map<String, Object> requestScopeFilter = filters.entrySet()
                .stream()
                .filter(map -> map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        return new ObjectMapper().convertValue(requestScopeFilter, RequestScope.class);
    }

    /**
     * 过滤筛选条件
     *
     * @param filters
     * @return
     */
    private Map<String, Object> getFieldFilter(Map<String, Object> filters) {
        return filters.entrySet()
                .stream()
                .filter(map -> !map.getKey().startsWith("_"))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }


    /**
     * 分页
     *
     * @param requestScope
     * @return
     */
    private String pagination(RequestScope requestScope) {
        return " limit ?,?";
    }

    private void buildSelectQuery(SelectQuery selectQuery, Object[] args, String entity, Map<String, Object> filters, Map<String, Field> fieldMap) {
        int i = 0;
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            Filter filter = dataServiceHelper.getFilter(entry, fieldMap);
            buildQuery(selectQuery, entity, filter, fieldMap.get(filter.getKey()));
            args[i++] = filter.getValue();
        }
    }

    /**
     * build select query
     *
     * @param selectQuery
     * @param entity
     * @param filter
     * @param field
     */
    private void buildQuery(SelectQuery selectQuery, String entity, Filter filter, Field field) {
        log.info("filter:{}", filter);
        QueryPreparer preparer = new QueryPreparer();
        Object filterValue = preparer.addStaticPlaceHolder(filter.getValue());
        DbTable table = rdbService.getDbTable(entity);
        DbColumn dbColumn = getDbColumn(table, field);
        switch (filter.getOperatorEnum()) {
            case eq:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
            case gte:
                selectQuery.addCondition(BinaryCondition.greaterThanOrEq(dbColumn, filterValue));
                break;
            case lte:
                selectQuery.addCondition(BinaryCondition.lessThanOrEq(dbColumn, filterValue));
                break;
            default:
                selectQuery.addCondition(BinaryCondition.equalTo(dbColumn, filterValue));
                break;
        }
    }

    @Override
    public long count(String entity, Map<String, Object> filters) {
        Map<String, Object> fieldFilter = getFieldFilter(filters);
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);
        DbTable table = rdbService.getDbTable(entity);
        SelectQuery selectQuery = new SelectQuery()
                .addCustomColumns(FunctionCall.countAll())
                .addFromTable(table);
        Object[] args = new Object[fieldFilter.size()];

        buildSelectQuery(selectQuery, args, entity, fieldFilter, fieldMap);
        String sql = selectQuery.toString();
        log.info("count record ,entity:{},data:{},sql:{}", entity, fieldFilter, sql);
        return rdbService.getJdbcTemplate().queryForObject(sql, args, Long.class);
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
        Map<String, Field> primaryFieldIdMap = dataServiceHelper.getPrimaryFieldIdMap(entity);
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
        Map<String, Field> fieldIdMap = dataServiceHelper.getFieldIdMap(entity);
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
                    if (v == null) {
                        break;
                    }
                    DateTime date;
                    if (v instanceof Long) {
                        date = new DateTime((long) v);
                    } else {
                        DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        date = f.parseDateTime(v.toString());
                    }
                    data.put(k, date == null ? null : JdbcEscape.timestamp(date.toDate()));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        DbTable table = rdbService.getDbTable(entity);
        List<Object> args = new LinkedList<>();

        UpdateQuery updateQuery = new UpdateQuery(table);
        Map<String, Field> fieldIdMap = dataServiceHelper.getFieldIdMap(entity);

        wrapData(fieldIdMap, data);
        data.forEach((k, v) -> {
            if (!Constants.id.equals(k) && fieldIdMap.containsKey(k) && fieldIdMap.get(k).getShowInEdit()) {
                args.add(v);
                QueryPreparer preparer = new QueryPreparer();
                Object setValue = preparer.addStaticPlaceHolder(v);
                updateQuery.addSetClause(getDbColumn(table, fieldIdMap.get(k)), setValue);
            }
        });
        if (DbTypeEnum.cds.equals(Tenant.get().getCurrentDataSource().getType())) {
            Optional<Map.Entry<String, Field>> optionalShardKey = fieldIdMap.entrySet().stream().filter(entry -> Boolean.TRUE.equals(entry.getValue().getIsShardKey())).findAny();
            if (!optionalShardKey.isPresent()) {
                throw new RuntimeException("cds数据源更新操作必须包含切分键");
            }
            Map.Entry<String, Field> shardKey = optionalShardKey.get();
            args.add(data.get(shardKey.getKey()));
            QueryPreparer preparer = new QueryPreparer();
            Object whereValue = preparer.addStaticPlaceHolder(data.get(shardKey.getKey()));
            updateQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, shardKey.getValue()), whereValue));
        }
        fieldIdMap.forEach((k, v) -> {
            if (v.getIsPartOfPrimaryKey() && !Boolean.TRUE.equals(v.getIsShardKey())) {
                args.add(data.get(k));
                QueryPreparer preparer = new QueryPreparer();
                Object whereValue = preparer.addStaticPlaceHolder(v);
                updateQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, v), whereValue));
            }
        });
        String sql = updateQuery.toString();
        log.info("update record , entity:{},data:{},sql:{}", entity, data, sql);
        rdbService.getJdbcTemplate().update(sql, args.toArray());
        return data;
    }

    @Override
    public String delete(String entity, String id) {
        Map<String, Field> primaryFieldIdMap = dataServiceHelper.getPrimaryFieldIdMap(entity);
        DbTable table = rdbService.getDbTable(entity);
        String[] idValues = id.split(Constants.delimiter);
        DeleteQuery deleteQuery = new DeleteQuery(table);
        int idx = 0;
        primaryFieldIdMap.forEach((k, v) -> {
            deleteQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, primaryFieldIdMap.get(k)), idValues[idx]));
        });
        if (DbTypeEnum.cds.equals(Tenant.get().getCurrentDataSource().getType())) {
            Map<String, Field> fieldIdMap = dataServiceHelper.getFieldIdMap(entity);
            Optional<Map.Entry<String, Field>> optionalShardKey = fieldIdMap.entrySet().stream().filter(entry -> Boolean.TRUE.equals(entry.getValue().getIsShardKey())).findAny();
            if (!optionalShardKey.isPresent()) {
                throw new RuntimeException("cds数据源更新操作必须包含切分键");
            }
            Map.Entry<String, Field> shardKey = optionalShardKey.get();
            Map<String, Object> record = findOne(entity, id);
            deleteQuery.addCondition(BinaryCondition.equalTo(getDbColumn(table, shardKey.getValue()), record.get(shardKey.getKey())));
        }
        log.info("delete record , entity:{},data:{},sql:{}", entity, id, deleteQuery);
        rdbService.getJdbcTemplate().execute(deleteQuery.toString());
        return id;
    }

    public Map<String, Object> addIdValue(String entity, Map<String, Object> data) {
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);
        String idValue = fieldMap.entrySet().stream().filter(field -> field.getValue().getIsPartOfPrimaryKey())
                .map(field -> data.get(field.getValue().getName()).toString())
                .collect(Collectors.joining(Constants.delimiter));
        data.put(Constants.id, idValue);
        return data;
    }

    private DbColumn getDbColumn(DbTable table, Field field) {
        return new DbColumn(table, field.getName(), String.valueOf(componentStringMap.get(field.getComponent())));
    }
}
