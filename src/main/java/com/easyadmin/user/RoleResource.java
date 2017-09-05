package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.AuthorityName;
import com.easyadmin.security.security.Role;
import com.easyadmin.service.DbService;
import com.easyadmin.service.SequenceService;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gongxinyi on 2017-09-01.
 */
@Slf4j
@RestController
public class RoleResource {
    @Autowired
    DbService dbService;
    @Autowired
    SequenceService sequenceService;

    @GetMapping("/role/_roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Role>> list() {
        List<Role> roles = dbService.getDataStore().createQuery(Role.class).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", roles.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(roles);
    }

    @GetMapping("/role/_roles/{roleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> findRole(@PathVariable("roleId") String roleId) {
        Role role = dbService.getDataStore().get(Role.class, roleId);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/role/_roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> addRole(@RequestBody final Role role) {
        role.setId(sequenceService.getNextSequence(Constants.SYS_COL_ROLE + Constants._id).toString());
        dbService.getDataStore().save(role);

        return ResponseEntity.ok(role);
    }

    @PutMapping(value = "/role/_roles/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Role> editRole(@PathVariable("id") String id, @RequestBody Role role) {
        // ROLE_ADMIN cannot to modify
        if (AuthorityName.ROLE_ADMIN.equals(role.getId())) return ResponseEntity.ok(role);
        final Query<Role> roleQuery = dbService.getDataStore().createQuery(Role.class).field("id").equal(id);
        final UpdateOperations<Role> updateOperations = dbService.getDataStore().createUpdateOperations(Role.class)
                .set("name", role.getName());

        dbService.getDataStore().update(roleQuery, updateOperations);
        return ResponseEntity.ok(role);
    }
}
