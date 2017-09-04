package com.easyadmin.security.security.repository;

import com.easyadmin.security.security.User;
import com.easyadmin.service.DbUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by gongxinyi on 2017-08-29.
 */
@Component
public class MongoUserRepository implements UserRepository {

    public User findByUsername(String username) {
        List<User> users = DbUtil.getDataStore().createQuery(User.class)
                .field("username").equal(username)
                .asList();

        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
