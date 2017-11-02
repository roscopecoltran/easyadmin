package com.easyadmin.security.security.repository;

import com.easyadmin.cloud.Tenant;
import com.easyadmin.security.security.User;
import com.easyadmin.service.MongoDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 *
 * @author gongxinyi
 * @date 2017-08-29
 */
@Component
@Slf4j
public class MongoUserRepository implements UserRepository {

    @Autowired
    MongoDbService mongoDbService;

    @Override
    public User findByUsername(String username) {
        List<Tenant> tenants = mongoDbService.getSysDataStore().createQuery(Tenant.class).field("users").equal(username).asList();
        if (CollectionUtils.isEmpty(tenants) || tenants.size() > 1) {
            log.error("exist one user in multiple org!!! user :{}", username);
            return null;
        }
        Tenant.set(tenants.get(0));

        List<User> users = mongoDbService.getDataStore().createQuery(User.class)
                .field("username").equal(username)
                .asList();

        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
