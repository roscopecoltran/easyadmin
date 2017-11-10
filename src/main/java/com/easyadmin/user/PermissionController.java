package com.easyadmin.user;

import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.Permission;
import com.easyadmin.service.SequenceService;
import com.easyadmin.service.SysService;
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

/**
 * Created by gongxinyi on 2017-09-04.
 */
@Slf4j
@RestController
public class PermissionController {
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;

    @PostMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> addPermission(@RequestBody final Permission permission) {
        permission.setId(sequenceService.getNextSequence(Constants.SYS_COL_USER + Constants._id).toString());
        sysService.getTenantDataStore().save(permission);

        return ResponseEntity.ok(permission);
    }

    @GetMapping("/permission/_permission")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Permission>> list(@RequestParam("roleId") String roleId) {
        List<Permission> permissions = StringUtils.isEmpty(roleId) ? sysService.getTenantDataStore().createQuery(Permission.class).asList() : sysService.getTenantDataStore().find(Permission.class).field("roleId").equal(roleId).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", permissions.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(permissions);
    }

    @GetMapping("/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> findUser(@PathVariable("id") String id) {

        Permission permission = sysService.getTenantDataStore().get(Permission.class, id);
        return ResponseEntity.ok(permission);
    }

    @PutMapping(value = "/permission/_permission/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Permission> editPermission(@PathVariable("id") String id, @RequestBody Permission permission) {
        Query<Permission> updateQuery = sysService.getTenantDataStore().createQuery(Permission.class).field("_id").equal(id);
        UpdateOperations<Permission> ops = sysService.getTenantDataStore()
                .createUpdateOperations(Permission.class)
                .set("eid", permission.getEid())
                .set("c", permission.isC())
                .set("r", permission.isR())
                .set("u", permission.isU())
                .set("d", permission.isD());
        sysService.getTenantDataStore().update(updateQuery, ops);
        return ResponseEntity.ok(permission);
    }

}
