package com.easyadmin.cloud;

import com.easyadmin.SysConfig;
import com.easyadmin.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@RestController
public class ApplyResource {
    @Autowired
    DbService dbService;
    @Autowired
    SysConfig config;

    @Autowired
    TenantService tenantService;

    @PostMapping(value = "/apply")
    public ResponseEntity<Apply> apply(@RequestBody Apply apply) {
        List<Tenant> tenantList = dbService.getSysDataStore().createQuery(Tenant.class).field("users").equal(apply.getUserName()).asList();
        if (!CollectionUtils.isEmpty(tenantList)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        dbService.getSysDataStore().save(apply);
        if (!config.isCheckforapply()) tenantService.createTenantFromApply(apply);
        return ResponseEntity.ok(apply);
    }
}
