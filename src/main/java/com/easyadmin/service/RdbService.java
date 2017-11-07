package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Slf4j
@org.springframework.stereotype.Component
public class RdbService {
    HikariDataSource ds;
    @Autowired
    MongoDbService mongoDbService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    Map<JDBCType, Component> fieldTypeMap;

    @Autowired
    SchemaService schemaService;

    public String getSchema() {
        return "financial_cf_credit_icr";
    }

    public DataSource getDataSource() {
        if (ds == null) {
            ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:mysql://172.24.7.29:3306/financial_cf_credit_icr");
            ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
            ds.setUsername("root");
            ds.setPassword("123456");
        }
        return ds;
    }

    public JdbcTemplate getJdbcTemplate() {
        DataSource dataSource = getDataSource();
        return new JdbcTemplate(dataSource);
    }

    public Collection<Table> getDbSchemas(String schema) throws Exception {
        Connection connection = getDataSource().getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schema));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        return catalog.getTables();
    }

    public DbTable getDbTable(String tableName) {
        DbSpec spec = new DbSpec(getSchema());
        DbSchema dbSchema = new DbSchema(spec, getSchema());
        return new DbTable(dbSchema, tableName);
    }

    /**
     * 同步schema
     *
     * @param schema
     * @throws Exception
     */
    public void syncSchemas(String schema) throws Exception {
        List<Entity> entityList = schemaService.findEntities();
        Collection<Table> tables = getDbSchemas(schema);
        for (Table table : tables) {
            if (table.getPrimaryKey() == null) {
                log.error("table : {} hasn't primary key", table.getName());
                continue;
            }
            Optional<Entity> optionalEntity = entityList.stream().filter(entity -> entity.getId().equals(table.getName())).findAny();
            if (!optionalEntity.isPresent()) {
                Entity entity = new Entity();
                entity.setId(table.getName());
                entity.setLabel(table.getName());
                mongoDbService.getDataStore().save(entity);
                for (Column column : table.getColumns()) {
                    Field field = column2Field(column, entity.getId());
                    mongoDbService.getDataStore().save(field);
                }
            } else {
                Entity findEntity = optionalEntity.get();
                for (Column column : table.getColumns()) {
                    Optional<Field> findField = findEntity.getFields().stream().filter(field -> field.getName().equals(column.getName())).findAny();
                    if (!findField.isPresent()) {
                        Field field = column2Field(column, findEntity.getId());
                        mongoDbService.getDataStore().save(field);
                    }
                }
                findEntity.getFields().stream().forEach(field -> {
                    boolean exists = table.getColumns().stream().filter(column -> column.getName().equals(field.getName())).findAny().isPresent();
                    if (!exists) {
                        mongoDbService.getDataStore().delete(field);
                    }
                });
            }
        }
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
