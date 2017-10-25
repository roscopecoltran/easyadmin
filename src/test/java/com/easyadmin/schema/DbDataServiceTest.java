package com.easyadmin.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void list() throws JsonProcessingException {
        String entity = "cf_credit_log";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        log.info("result:{}", objectMapper.writeValueAsString(jdbcTemplate.queryForList("select * from " + entity)));
    }


}
