package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.JwtTokenUtil;
import com.easyadmin.security.security.JwtUser;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-09-01.
 */
@RestController
public class UserResource {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/user/_users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> list() {
        List<User> users = new ArrayList<>();
        Block<Document> wrapUsersBlock = doc -> {
            User user = mapper.convertValue(doc, User.class);
            users.add(user);
        };

        DbUtil.getCollection(Constants.SYS_COL_USER).find().forEach(wrapUsersBlock);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", users.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(users);
    }

    @GetMapping(value = "/user/me")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}
