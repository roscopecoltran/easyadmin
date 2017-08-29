package com.easyadmin.security.security.repository;

import com.easyadmin.security.security.Authority;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2017-08-29.
 */
@Component
public class MongoUserRepository implements UserRepository {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public User findByUsername(String username) {
        List<User> users = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("username", username);
        Block<Document> wrapUserBlock = doc -> users.add(mapper.convertValue(doc, User.class));
        DbUtil.getCollection("_user").find(query).forEach(wrapUserBlock);

        List<Authority> authoritys = new ArrayList<>();
        Block<Document> wrapAuthoritysBlock = doc -> authoritys.add(mapper.convertValue(doc, Authority.class));
        DbUtil.getCollection("_role").find().forEach(wrapAuthoritysBlock);

        User user = users.get(0);
        if (user == null) return null;

        BasicDBObject query2 = new BasicDBObject("user_id", String.valueOf(user.getId()));
        Set<String> roles = new HashSet<>();
        Block<Document> wrapRolesBlock = doc -> roles.add(doc.getString("role_id"));
        DbUtil.getCollection("_user_role").find(query2).forEach(wrapRolesBlock);

        List<Authority> userAuthoritys = authoritys.stream().filter(authority -> roles.contains(String.valueOf(authority.getId()))).collect(Collectors.toList());
        user.setAuthorities(userAuthoritys);
        return user;
    }
}
