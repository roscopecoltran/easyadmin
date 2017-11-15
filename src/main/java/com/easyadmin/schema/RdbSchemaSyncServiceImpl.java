package com.easyadmin.schema;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.DbColumnType;
import com.easyadmin.schema.enums.InputType;
import com.easyadmin.service.RdbService;
import com.easyadmin.service.SequenceService;
import com.easyadmin.service.SysService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

import java.sql.Connection;
import java.sql.JDBCType;
import java.util.*;

/**
 * Created by gongxinyi on 2017-11-13.
 */
@Slf4j
@org.springframework.stereotype.Component
public class RdbSchemaSyncServiceImpl implements ISchemaSyncService {
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    Map<JDBCType, Component> fieldTypeMap;
    @Autowired
    Map<Component, DbColumnType> componentStringMap;
    @Autowired
    SchemaService schemaService;
    @Autowired
    RdbService rdbService;

    public Collection<Table> getDbSchemas(String schema) throws Exception {
        Connection connection = rdbService.getJdbcTemplate().getDataSource().getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schema));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        return catalog.getTables();
    }

    /**
     * 同步schema
     *
     * @throws Exception
     */
    @Override
    public void syncSchemas() {
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        try {
            List<Entity> entities = schemaService.findEntities();
            String schema = sysService.getTenantDataStore().get(DataSource.class, dataSource.getId()).getMySqlDbName();
            Collection<Table> tables = getDbSchemas(schema);
            for (Table table : tables) {
                if (table.getPrimaryKey() == null) {
                    log.error("table : {} hasn't primary key", table.getName());
                    continue;
                }
                Optional<Entity> optionalEntity = entities.stream().filter(entity -> dataSource.getId().equals(entity.getDataSourceId()) && entity.getId().equals(table.getName())).findAny();
                if (!optionalEntity.isPresent()) {
                    addEntityAndFields(table, dataSource.getId());
                } else {
                    Entity findEntity = optionalEntity.get();
                    for (Column column : table.getColumns()) {
                        Optional<Field> findField = findEntity.getFields().stream().filter(field -> field.getName().equals(column.getName())).findAny();
                        if (!findField.isPresent()) {
                            Field field = column2Field(column, findEntity.getId());
                            field.setDataSourceId(dataSource.getId());
                            sysService.getTenantDataStore().save(field);
                        }
                    }
                    findEntity.getFields().stream().forEach(field -> {
                        boolean exists = table.getColumns().stream().filter(column -> column.getName().equals(field.getName())).findAny().isPresent();
                        if (!exists) {
                            sysService.getTenantDataStore().delete(field);
                        }
                    });
                }
            }
        } catch (Exception e) {
            log.error("sync schema error!!! datasource:{}", dataSource);
        }
    }


    /**
     * add new entity and its fields when entity is not exists
     *
     * @param table
     * @param dataSourceId
     * @throws JsonProcessingException
     */
    private void addEntityAndFields(Table table, String dataSourceId) throws JsonProcessingException {
        Entity entity = new Entity();
        entity.setDataSourceId(dataSourceId);
        String id = sequenceService.getNextSequence(Constants.SYS_COL_Entity + "_id").toString();
        entity.setId(Constants.ENTITY_NAME_PREFIX + id);
        entity.setName(table.getName());
        entity.setLabel(StringUtils.isEmpty(table.getRemarks()) ? table.getName() : table.getRemarks());
        entity.setShowInMenu(true);
        sysService.getTenantDataStore().save(entity);
        List<Field> fields = new ArrayList<>();
        for (Column column : table.getColumns()) {
            Field field = column2Field(column, entity.getId());
            field.setDataSourceId(dataSourceId);
            fields.add(field);
        }
        sysService.getTenantDataStore().save(fields);
    }

    /**
     * 数据库字段转换成field对象
     *
     * @param column
     * @param eid
     * @return
     */
    private Field column2Field(Column column, String eid) throws JsonProcessingException {
        log.info("column : {},{},{}", column, column.isGenerated(), column.isAutoIncremented());
        Field field = new Field();
        field.setIsAutoIncremented(isAutoField(column));
        field.setIsPartOfPrimaryKey(column.isPartOfPrimaryKey());
        field.setName(column.getName());
        field.setLabel(StringUtils.isEmpty(column.getRemarks()) ? column.getName() : column.getRemarks());
        field.setEid(eid);
        field.setMaxLength(column.getSize());
        field.setRequired(!column.isNullable());
        field.setDefaultValue(column.getDefaultValue());
        field.setComponent(getComponent(column));
        field.setInputType(InputType.text);
        field.setShowInFilter(true);
        field.setShowInCreate(!Component.Date.equals(field.getComponent()) && field.getRequired());
        field.setShowInEdit(!Component.Date.equals(field.getComponent()));
        field.setOriginalDbColumnType(componentStringMap.get(getComponent(column)));
        field.setShowInShow(true);
        String fid = sequenceService.getNextSequence(Constants.SYS_COL_Field + Constants._id).toString();
        field.setId(Constants.FIELD_NAME_PREFIX + fid);
        return field;
    }

    private boolean isAutoField(Column column) {
        return (Component.Date.equals(getComponent(column)) &&
                "CURRENT_TIMESTAMP".equals(column.getDefaultValue())) || column.isAutoIncremented();
    }

    private Component getComponent(Column column) {
        Component component = fieldTypeMap.get(JDBCType.valueOf(column.getColumnDataType().getJavaSqlType().getJavaSqlType()));
        if (component == null) {
            component = Component.Text;
        }
        return component;
    }
}
