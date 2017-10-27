package com.easyadmin.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmarketscience.sqlbuilder.BinaryCondition;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.SelectQuery;
import com.healthmarketscience.sqlbuilder.UpdateQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSchema;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbSpec;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * Created by gongxinyi on 2017-10-25.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbDataServiceTest {

    @Autowired
    DataSource ds;
    final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void listAll() throws JsonProcessingException {
        String entity = "cf_credit_log";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        log.info("result:{}", objectMapper.writeValueAsString(jdbcTemplate.queryForList("select * from " + entity)));
    }

    @Test
    public void insert() {
        DbSpec spec = new DbSpec("test_easyadmin");
        DbSchema schema = new DbSchema(spec, "test_easyadmin");
        DbTable customerTable = new DbTable(schema, "test_easyadmin");
        DbColumn test_int = new DbColumn(customerTable, "test_int", "int");
        DbColumn test_varchar = new DbColumn(customerTable, "test_varchar", "varchar");
        String insertCustomerQuery =
                new InsertQuery(customerTable)
                        .addColumn(test_int, 1)
                        .addColumn(test_varchar, "bob")
                        .validate().toString();
        System.out.println(insertCustomerQuery);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute(insertCustomerQuery);
    }

    @Test
    public void update() {
        DbSpec spec = new DbSpec("test_easyadmin");
        DbSchema schema = new DbSchema(spec, "test_easyadmin");
        DbTable customerTable = new DbTable(schema, "test_easyadmin");
        DbColumn id = new DbColumn(customerTable, "id", "int");
        DbColumn test_int = new DbColumn(customerTable, "test_int", "int");
        DbColumn test_varchar = new DbColumn(customerTable, "test_varchar", "varchar");
        String updateQuery1 = new UpdateQuery(customerTable)
                .addSetClause(test_int, 47)
                .addSetClause(test_varchar, "foo")
                .addCondition(BinaryCondition.equalTo(id, 1))
                .validate().toString();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute(updateQuery1);
    }

    @Test
    public void list() throws JsonProcessingException {
        DbSpec spec = new DbSpec("test_easyadmin");
        DbSchema schema = new DbSchema(spec, "test_easyadmin");
        DbTable customerTable = new DbTable(schema, "test_easyadmin");
        DbColumn test_int = new DbColumn(customerTable, "test_int", "int");
        DbColumn test_varchar = new DbColumn(customerTable, "test_varchar", "varchar");
        String query1 =
                new SelectQuery()
                        .addColumns(test_int)
                        .addColumns(test_varchar)
                        .addCondition(BinaryCondition.equalTo(test_int, 1))
                        .validate().toString();
        System.out.println(query1);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        log.info("result:{}", objectMapper.writeValueAsString(jdbcTemplate.queryForList(query1)));
    }
}
