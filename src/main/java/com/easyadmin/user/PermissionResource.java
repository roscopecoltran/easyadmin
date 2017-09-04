package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.security.security.Permission;
import com.easyadmin.security.security.User;
import com.easyadmin.service.DataService;
import com.easyadmin.service.DbUtil;
import com.easyadmin.service.SequenceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
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
public class PermissionResource {
    @Autowired
    DataService dataService;

    @PostMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> addPermission(@RequestBody final Permission permission) {
        permission.setId(SequenceUtil.getNextSequence(Constants.SYS_COL_USER + Constants._id).toString());
        DbUtil.getDataStore().save(permission);

        return ResponseEntity.ok(permission);
    }

    @GetMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Permission>> list(@RequestParam("roleId") String roleId) {
        List<Permission> permissions = StringUtils.isEmpty(roleId) ? DbUtil.getDataStore().createQuery(Permission.class).asList() : DbUtil.getDataStore().find(Permission.class).field("roleId").equal(roleId).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", permissions.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(permissions);
    }

    @GetMapping("/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> findUser(@PathVariable("id") String id) {

        Permission permission = DbUtil.getDataStore().get(Permission.class, id);
        return ResponseEntity.ok(permission);
    }

    @PutMapping(value = "/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> editField(@PathVariable("id") String id, @RequestBody Permission permission) {
        dataService.update(Constants.SYS_COL_PERMISSION, id, new ObjectMapper().convertValue(permission, Map.class));
        return ResponseEntity.ok(permission);
    }

}
