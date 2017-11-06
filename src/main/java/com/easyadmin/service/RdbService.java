package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.ColumnDataType;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.JDBCType;
import java.util.Collection;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@org.springframework.stereotype.Component
public class RdbService {
    HikariDataSource ds;
    @Autowired
    MongoDbService mongoDbService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    Map<JDBCType, Component> fieldTypeMap;

    public String getSchema() {
        return "test_easyadmin";
    }

    public DataSource getDataSource() {
        if (ds == null) {
            ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:mysql://172.24.7.29:3306/test_easyadmin");
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

    public void syncSchemas(String schema) throws Exception {
        Collection<Table> tables = getDbSchemas(schema);
        for (Table table : tables) {
            Entity entity = new Entity();
            entity.setId(table.getName());
            entity.setLabel(table.getName());
            mongoDbService.getDataStore().save(entity);
            for (Column column : table.getColumns()) {
                Field field = new Field();
                field.setIsAutoIncremented(column.isAutoIncremented());
                field.setIsPartOfPrimaryKey(column.isPartOfPrimaryKey());
                field.setName(column.getName());
                field.setLabel(StringUtils.isEmpty(column.getRemarks()) ? column.getName() : column.getRemarks());
                field.setEid(entity.getId());
                field.setMaxLength(column.getSize());
                field.setRequired(!column.isNullable());
                field.setDefaultValue(column.getDefaultValue());
                ColumnDataType columnDataType = column.getColumnDataType();
                field.setComponent(fieldTypeMap.get(JDBCType.valueOf(columnDataType.getJavaSqlType().getJavaSqlType())));
                String fid = sequenceService.getNextSequence(Constants.SYS_COL_Field + "_id").toString();
                field.setId(Constants.FIELD_NAME_PREFIX + fid);
                mongoDbService.getDataStore().save(field);
            }
        }
    }

}
