package com.easyadmin.security.security.repository;

import com.easyadmin.cloud.Tenant;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by gongxinyi on 2017-08-29.
 */
@Component
public class MongoUserRepository implements UserRepository {

    @Autowired
    DbService dbService;

    public User findByUsername(String username) {
        List<Tenant> tenants = dbService.getSysDataStore().createQuery(Tenant.class).field("users").equal(username).asList();
        if (CollectionUtils.isEmpty(tenants)) return null;
        Tenant.set(tenants.get(0));

        List<User> users = dbService.getDataStore().createQuery(User.class)
                .field("username").equal(username)
                .asList();

        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
