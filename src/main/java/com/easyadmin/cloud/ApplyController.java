package com.easyadmin.cloud;

import com.easyadmin.SysConfig;
import com.easyadmin.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gongxinyi on 2017-09-09.
 */
@RestController
public class ApplyController {
    @Autowired
    DbService dbService;
    @Autowired
    SysConfig config;

    @Autowired
    TenantService tenantService;

    @PostMapping(value = "/apply")
    public ResponseEntity apply(@RequestBody @Validated Apply apply) {
        List<Tenant> tenantList = dbService.getSysDataStore().createQuery(Tenant.class).field("users").equal(apply.getUsername()).asList();
        if (!CollectionUtils.isEmpty(tenantList)) throw new IllegalStateException();
        dbService.getSysDataStore().save(apply);
        if (!config.isCheckforapply()) tenantService.createTenantFromApply(apply);
        return ResponseEntity.ok(apply);
    }
}
