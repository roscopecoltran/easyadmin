package com.easyadmin.cloud;

import com.easyadmin.service.DbService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TenantTest {
    @Autowired
    DbService dbService;

    @Test
    public void findUser() {
        // create tenant
//        Tenant tenant = new Tenant();
//        tenant.setId("testOrg1");
//        tenant.setConnectionStr("mongodb://localhost:27017/easyadmin");
//        tenant.setDbName("easyadmin");
//        dbService.getSysDataStore().save(tenant);

//        Tenant tenant2 = new Tenant();
//        tenant2.setId("testOrg2");
//        tenant2.setConnectionStr("mongodb://localhost:27017/testdb1");
//        tenant2.setDbName("testdb1");
//        dbService.getSysDataStore().save(tenant2);

        // add user

//        final Query<Tenant> tenantQuery = dbService.getSysDataStore().createQuery(Tenant.class)
//                .filter("id =", "testOrg1");
//        final UpdateOperations<Tenant> userUpdate = dbService.getSysDataStore().createUpdateOperations(Tenant.class)
//                .push("users", "admin");

        final Query<Tenant> tenantQuery2 = dbService.getSysDataStore().createQuery(Tenant.class)
                .filter("id =", "testOrg2");
        final UpdateOperations<Tenant> userUpdate2 = dbService.getSysDataStore().createUpdateOperations(Tenant.class)
                .push("users", "testadmin3");

        dbService.getSysDataStore().update(tenantQuery2, userUpdate2);

        // find user
        List<Tenant> tenants = dbService.getSysDataStore().createQuery(Tenant.class).field("users").equal("testadmin2").asList();
        log.info("");
    }
}
