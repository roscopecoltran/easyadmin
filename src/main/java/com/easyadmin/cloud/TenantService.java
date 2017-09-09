package com.easyadmin.cloud;

import com.easyadmin.security.security.Role;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbService;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@Component
public class TenantService {
    @Autowired
    MongoProperties properties;
    @Autowired
    DbService dbService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Tenant createTenantFromApply(Apply apply) {
        // create tenant
        String tenantId = randomString();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setConnectionStr(properties.getUri());
        tenant.setDbName(tenantId);

        // create user
        String[] user = {apply.getUserName()};
        tenant.setUsers(user);

        dbService.getSysDataStore().save(tenant);

        // init db and users , roles
        initUserAndRole(tenant, apply.getUserName());
        return tenant;
    }

    public void initUserAndRole(Tenant tenant, String userName) {
        MongoClientURI uri = new MongoClientURI(tenant.getConnectionStr());
        MongoClient client = new MongoClient(uri);
        Datastore datastore = new Morphia().createDatastore(client, tenant.getDbName());
        Role role = new Role();
        role.setId("ROLE_ADMIN");
        role.setName("ROLE_ADMIN");
        datastore.save(role);

        User user = new User();
        user.setId("1");
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode("88888888"));
        user.setEnabled(true);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        datastore.save(user);
    }

    public static String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
