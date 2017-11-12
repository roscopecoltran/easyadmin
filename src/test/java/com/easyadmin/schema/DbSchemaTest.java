package com.easyadmin.schema;

import com.easyadmin.service.RdbService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import schemacrawler.schema.Column;
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
        Collection<Table> dbTable = rdbService.getDbSchemas();
        dbTable.stream().forEach(table -> {
            if("cf_credit_log".equals(table.getName())){
                Collection<Column> columns=table.getColumns();
                for(Column column:columns){
                    log.info("table:{},column:{}", table.getName(), column);
                }
            }

        });
    }
}
