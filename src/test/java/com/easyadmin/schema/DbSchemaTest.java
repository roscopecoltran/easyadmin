package com.easyadmin.schema;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.ColumnDataType;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.utility.SchemaCrawlerUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by gongxinyi on 2017-10-24.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbSchemaTest {
    @Autowired
    DataSource ds;
    final ObjectMapper objectMapper = new ObjectMapper();
    static Map<JDBCType, Component> fieldTypeMap = new HashMap<JDBCType, Component>();

    static {
        fieldTypeMap.put(JDBCType.CHAR, Component.Text);
        fieldTypeMap.put(JDBCType.VARCHAR, Component.Text);
        fieldTypeMap.put(JDBCType.LONGNVARCHAR, Component.Text);

        fieldTypeMap.put(JDBCType.NUMERIC, Component.Number);
        fieldTypeMap.put(JDBCType.DECIMAL, Component.Number);

        fieldTypeMap.put(JDBCType.BIT, Component.Boolean);

        fieldTypeMap.put(JDBCType.TINYINT, Component.Number);
        fieldTypeMap.put(JDBCType.SMALLINT, Component.Number);

        fieldTypeMap.put(JDBCType.INTEGER, Component.Number);
        fieldTypeMap.put(JDBCType.BIGINT, Component.Number);
        fieldTypeMap.put(JDBCType.REAL, Component.Number);
        fieldTypeMap.put(JDBCType.FLOAT, Component.Number);
        fieldTypeMap.put(JDBCType.DOUBLE, Component.Number);

        fieldTypeMap.put(JDBCType.BINARY, Component.Text);
        fieldTypeMap.put(JDBCType.VARBINARY, Component.Text);
        fieldTypeMap.put(JDBCType.LONGVARBINARY, Component.Text);

        fieldTypeMap.put(JDBCType.DATE, Component.Date);
        fieldTypeMap.put(JDBCType.TIME, Component.Date);
        fieldTypeMap.put(JDBCType.TIMESTAMP, Component.Date);
    }

    @Test
    public void dbMeta() throws SQLException, SchemaCrawlerException, JsonProcessingException {
        Connection connection = ds.getConnection();

        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        options.setSchemaInclusionRule(new RegularExpressionInclusionRule("financial_cf_credit_icr"));
        Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        Collection<Table> tables = catalog.getTables();
        List<Entity> entityList = new ArrayList<Entity>();
        for (Table table : tables) {
            Entity entity = new Entity();
            entity.setId(table.getName());
            entity.setLabel(table.getRemarks());
            List<Field> fieldList = new ArrayList<Field>();
            for (Column column : table.getColumns()) {
                Field field = new Field();
                field.setId(column.getName());
                field.setLabel(column.getRemarks());
                field.setEid(entity.getId());
                field.setMaxLength(column.getSize());
                field.setRequired(!column.isNullable());
                field.setDefaultValue(column.getDefaultValue());
                ColumnDataType columnDataType = column.getColumnDataType();
                field.setComponent(fieldTypeMap.get(JDBCType.valueOf(columnDataType.getJavaSqlType().getJavaSqlType())));
                fieldList.add(field);
            }
            entity.setFields(fieldList);
            entityList.add(entity);
        }
        log.info("schemas : {} ", objectMapper.writeValueAsString(entityList));
    }
}
