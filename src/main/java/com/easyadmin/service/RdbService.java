package com.easyadmin.service;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2017-11-02
 */
@Component
public class RdbService {
    HikariDataSource ds;
    @Autowired
    MongoDbService mongoDbService;
    @Autowired
    SequenceService sequenceService;

    static Map<JDBCType, com.easyadmin.schema.enums.Component> fieldTypeMap = new HashMap<JDBCType, com.easyadmin.schema.enums.Component>();

    static {
        fieldTypeMap.put(JDBCType.CHAR, com.easyadmin.schema.enums.Component.Text);
        fieldTypeMap.put(JDBCType.VARCHAR, com.easyadmin.schema.enums.Component.Text);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, com.easyadmin.schema.enums.Component.Text);

        fieldTypeMap.put(JDBCType.NUMERIC, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.DECIMAL, com.easyadmin.schema.enums.Component.Number);

        fieldTypeMap.put(JDBCType.BIT, com.easyadmin.schema.enums.Component.Boolean);

        fieldTypeMap.put(JDBCType.TINYINT, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.SMALLINT, com.easyadmin.schema.enums.Component.Number);

        fieldTypeMap.put(JDBCType.INTEGER, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.BIGINT, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.REAL, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.FLOAT, com.easyadmin.schema.enums.Component.Number);
        fieldTypeMap.put(JDBCType.DOUBLE, com.easyadmin.schema.enums.Component.Number);

        fieldTypeMap.put(JDBCType.BINARY, com.easyadmin.schema.enums.Component.Text);
        fieldTypeMap.put(JDBCType.VARBINARY, com.easyadmin.schema.enums.Component.Text);
        fieldTypeMap.put(JDBCType.LONGVARBINARY, com.easyadmin.schema.enums.Component.Text);

        fieldTypeMap.put(JDBCType.DATE, com.easyadmin.schema.enums.Component.Date);
        fieldTypeMap.put(JDBCType.TIME, com.easyadmin.schema.enums.Component.Date);
        fieldTypeMap.put(JDBCType.TIMESTAMP, com.easyadmin.schema.enums.Component.Date);
    }

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

    public Collection<Table> getDbSchemas(String schema) throws Exception {
        Connection connection = getDataSource().getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule(schema));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        return catalog.getTables();
    }

    public List<Column> getColumns(String schema, String tableName) throws Exception {
        Collection<Table> tables = getDbSchemas(schema);
        Table findTable = tables.stream().filter(table -> table.getName().equals(tableName)).findFirst().get();
        return findTable.getColumns();
    }

    public DbTable getDbTable(String schema, String tableName) {
        DbSpec spec = new DbSpec(schema);
        DbSchema dbSchema = new DbSchema(spec, schema);
        return new DbTable(dbSchema, tableName);
    }

    public DbColumn getDbColumn(String schema, String tableName, String fieldId) throws Exception {
        DbTable dbTable = getDbTable(schema, tableName);
        List<Column> columns = getColumns(schema, tableName);
        Column findColumn = columns.stream().filter(column -> column.getName().equals(fieldId)).findFirst().get();
        return new DbColumn(dbTable, fieldId, findColumn.getType().getName());
    }

    public void syncSchemas(String schema) throws Exception {
        Collection<Table> tables = getDbSchemas(schema);
        for (Table table : tables) {
            Entity entity = new Entity();
            entity.setId(table.getName());
            entity.setLabel(table.getName());
            mongoDbService.getDataStore().save(entity);
            for (Column column : table.getColumns()) {
                if ("id".equals(column.getName())) {
                    continue;
                }
                Field field = new Field();
                field.setId(column.getName());
                field.setName(column.getName());
                field.setLabel(StringUtils.isEmpty(column.getRemarks()) ? column.getName() : column.getRemarks());
                field.setEid(entity.getId());
                field.setMaxLength(column.getSize());
                field.setRequired(!column.isNullable());
                field.setDefaultValue(column.getDefaultValue());
                ColumnDataType columnDataType = column.getColumnDataType();
                field.setComponent(fieldTypeMap.get(JDBCType.valueOf(columnDataType.getJavaSqlType().getJavaSqlType())));
//                String fid = sequenceService.getNextSequence(Constants.SYS_COL_Field + "_id").toString();
//                field.setId(Constants.FIELD_NAME_PREFIX + fid);
                mongoDbService.getDataStore().save(field);
            }
        }
    }
}
