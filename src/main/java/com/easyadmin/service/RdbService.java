package com.easyadmin.service;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.schema.SchemaService;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
 * @author gongxinyi
 * @date 2017-11-02
 */
@Slf4j
@org.springframework.stereotype.Component
public class RdbService {
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    Map<JDBCType, Component> fieldTypeMap;

    @Autowired
    SchemaService schemaService;

    /**
     * all the tenant cache the datasource
     */
    @Autowired
    Map<String, JdbcTemplate> jdbcTemplateMap;

    public String getCurrentSchema() {
        return Tenant.get().getCurrentDataSource().getMySqlDbName();
    }

    private HikariDataSource getDataSource(String dataSourceId) {
        DataSource dataSource = sysService.getTenantDataStore().get(DataSource.class, dataSourceId);
        return dataSource2Hikari(dataSource);
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

    public Collection<Table> getDbSchemas(String schema) throws Exception {
        Connection connection = getJdbcTemplate().getDataSource().getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schema));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        return catalog.getTables();
    }

    public DbTable getDbTable(String tableName) {
        DbSpec spec = new DbSpec(getCurrentSchema());
        DbSchema dbSchema = new DbSchema(spec, getCurrentSchema());
        return new DbTable(dbSchema, tableName);
    }

    /**
     * 同步schema
     *
     * @param dataSourceId
     * @throws Exception
     */
    public void syncSchemas(String dataSourceId) throws Exception {
        List<Entity> entities = schemaService.findEntities();
        String schema = sysService.getTenantDataStore().get(DataSource.class, dataSourceId).getMySqlDbName();
        Collection<Table> tables = getDbSchemas(schema);
        for (Table table : tables) {
            if (table.getPrimaryKey() == null) {
                log.error("table : {} hasn't primary key", table.getName());
                continue;
            }
            Optional<Entity> optionalEntity = entities.stream().filter(entity -> dataSourceId.equals(entity.getDataSourceId()) && entity.getId().equals(table.getName())).findAny();
            if (!optionalEntity.isPresent()) {
                addEntityAndFields(table, dataSourceId);
            } else {
                Entity findEntity = optionalEntity.get();
                for (Column column : table.getColumns()) {
                    Optional<Field> findField = findEntity.getFields().stream().filter(field -> field.getName().equals(column.getName())).findAny();
                    if (!findField.isPresent()) {
                        Field field = column2Field(column, findEntity.getId());
                        field.setDataSourceId(dataSourceId);
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
        entity.setLabel(StringUtils.isEmpty(table.getRemarks())?table.getName():table.getRemarks());
        entity.setShowInMenu(true);
        sysService.getTenantDataStore().save(entity);
        List<Field> fields=new ArrayList<>();
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
        field.setShowInList(true);
        field.setShowInFilter(true);
        field.setShowInCreate(true);
        field.setShowInEdit(true);
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
