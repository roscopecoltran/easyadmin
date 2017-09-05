package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.Role;
import com.easyadmin.security.security.User;
import com.easyadmin.security.security.repository.UserRepository;
import com.easyadmin.service.DbService;
import com.easyadmin.service.MorphiaFactory;
import com.easyadmin.service.SequenceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MorphiaFactory morphiaFactory;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    DbService dbService;
    @Test
    public void saveUser() {
        Role role = new Role();
        role.setId("ROLE_ADMIN");
        role.setName("ROLE_ADMIN");
        dbService.getDataStore().save(role);

        User user = new User();
        user.setId(sequenceService.getNextSequence(Constants.SYS_COL_USER + "_id").toString());
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEnabled(true);
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        dbService.getDataStore().save(user);
    }

    @Test
    public void findUserByName() {
        User user = userRepository.findByUsername("admin");
        Assert.assertNotNull(user);
    }

    @Test
    public void updateUserRole() throws JsonProcessingException {
        final Query<User> underPaidQuery = dbService.getDataStore().createQuery(User.class)
                .filter("username =", "user");

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId("2");
        roles.add(role);
        final UpdateOperations<User> updateOperations = dbService.getDataStore().createUpdateOperations(User.class)
                .set("roles", roles);

        final UpdateResults results = dbService.getDataStore().update(underPaidQuery, updateOperations);

        log.info("results:{}", new ObjectMapper().writeValueAsString(results));

    }

    @Test
    public void findUsers() throws JsonProcessingException {

        log.info("user:{}", new ObjectMapper().writeValueAsString(morphiaFactory.get().find(User.class).asList()));
    }
}
