package com.easyadmin.user;

import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.JwtTokenUtil;
import com.easyadmin.security.security.JwtUser;
import com.easyadmin.security.security.Role;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbService;
import com.easyadmin.service.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-09-01.
 */
@Slf4j
@RestController
public class UserController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DbService dbService;
    @Autowired
    SequenceService sequenceService;

    @GetMapping("/user/_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> list() {
        List<User> users = dbService.getDataStore().createQuery(User.class).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", users.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(users);
    }

    @GetMapping("/user/_users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> findUser(@PathVariable("userId") String userId) {

        User user = dbService.getDataStore().get(User.class, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("/user/_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody final User user) {
        user.setId(sequenceService.getNextSequence(Constants.SYS_COL_USER + Constants._id).toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dbService.getDataStore().save(user);


        // add user to the tenant , important !!! need keep transaction.
        final Query<Tenant> tenantQuery = dbService.getSysDataStore().createQuery(Tenant.class)
                .filter("id =", Tenant.get().getId());
        final UpdateOperations<Tenant> userUpdate = dbService.getSysDataStore().createUpdateOperations(Tenant.class)
                .push("users", user.getUsername());

        dbService.getSysDataStore().update(tenantQuery, userUpdate);

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/user/me")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    @PutMapping(value = "/user/_users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> editField(@PathVariable("id") String id, @RequestBody User user) {
        final Query<User> userQuery = dbService.getDataStore().createQuery(User.class).field("id").equal(id);
        final UpdateOperations<User> updateOperations = dbService.getDataStore().createUpdateOperations(User.class)
                .set("roles", user.getRoles())
                .set("enabled", user.getEnabled());

        dbService.getDataStore().update(userQuery, updateOperations);
        return ResponseEntity.ok(user);
    }

//    @PostConstruct


}
