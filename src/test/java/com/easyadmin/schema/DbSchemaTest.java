package com.easyadmin.schema;

import com.easyadmin.service.RdbService;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import schemacrawler.schema.Table;

import java.util.Collection;

/**
 * Created by gongxinyi on 2017-10-24.
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbSchemaTest {
    @Autowired
    RdbService rdbService;

    @Test
    public void testSchema() throws Exception {
        Collection<Table> dbTable = rdbService.getDbSchemas("test_easyadmin");
        dbTable.stream().forEach(table -> {
            log.info("dbTable:{}",table.getPrimaryKey());
        });
    }
}
