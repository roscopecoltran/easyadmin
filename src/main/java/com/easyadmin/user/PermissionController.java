package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.Permission;
import com.easyadmin.service.DataService;
import com.easyadmin.service.DbService;
import com.easyadmin.service.SequenceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-09-04.
 */
@Slf4j
@RestController
public class PermissionController {
    @Autowired
    DataService dataService;
    @Autowired
    DbService dbService;
    @Autowired
    SequenceService sequenceService;

    @PostMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> addPermission(@RequestBody final Permission permission) {
        permission.setId(sequenceService.getNextSequence(Constants.SYS_COL_USER + Constants._id).toString());
        dbService.getDataStore().save(permission);

        return ResponseEntity.ok(permission);
    }

    @GetMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Permission>> list(@RequestParam("roleId") String roleId) {
        List<Permission> permissions = StringUtils.isEmpty(roleId) ? dbService.getDataStore().createQuery(Permission.class).asList() : dbService.getDataStore().find(Permission.class).field("roleId").equal(roleId).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", permissions.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(permissions);
    }

    @GetMapping("/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> findUser(@PathVariable("id") String id) {

        Permission permission = dbService.getDataStore().get(Permission.class, id);
        return ResponseEntity.ok(permission);
    }

    @PutMapping(value = "/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> editField(@PathVariable("id") String id, @RequestBody Permission permission) {
        dataService.update(Constants.SYS_COL_PERMISSION, id, new ObjectMapper().convertValue(permission, Map.class));
        return ResponseEntity.ok(permission);
    }

}
