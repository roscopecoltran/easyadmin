package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.security.security.Authority;
import com.easyadmin.security.security.JwtTokenUtil;
import com.easyadmin.security.security.JwtUser;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DataService;
import com.easyadmin.service.DbUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-09-01.
 */
@Slf4j
@RestController
public class UserResource {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DataService dataService;

    @GetMapping("/user/_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> list() {
        List<User> users = new ArrayList<>();
        Block<Document> wrapUsersBlock = doc -> {
            User user = mapper.convertValue(doc, User.class);
            user.setPassword("");
            users.add(user);
        };

        DbUtil.getCollection(Constants.SYS_COL_USER).find().forEach(wrapUsersBlock);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", users.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(users);
    }

    @PostMapping("/user/_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody final Map<String, Object> allRequestParams) {
        User user = mapper.convertValue(allRequestParams, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Map<String, Object> userMap = dataService.save(Constants.SYS_COL_USER, mapper.convertValue(user, Map.class));
        List role_ids = (List) allRequestParams.get("roles");
        Map<String, Object> userRoleMap = new HashMap<>();
        userRoleMap.put("user_id", userMap.get(Constants._id).toString());
        for (Object role_id : role_ids) {
            userRoleMap.put("role_id", role_id.toString());
            dataService.save(Constants.SYS_COL_USER_ROLE, userRoleMap);
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/user/me")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
