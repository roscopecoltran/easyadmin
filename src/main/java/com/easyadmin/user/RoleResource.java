package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.Authority;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DbUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.Block;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-09-01.
 */
@Slf4j
@RestController
public class RoleResource {
    final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @GetMapping("/role/_roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Authority>> list() {
        List<Authority> roles = new ArrayList<>();
        Block<Document> wrapRolesBlock = doc -> {
            Authority role = mapper.convertValue(doc, Authority.class);
            roles.add(role);
        };

        DbUtil.getCollection(Constants.SYS_COL_ROLE).find().forEach(wrapRolesBlock);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", roles.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(roles);
    }
}
