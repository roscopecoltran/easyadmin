package com.easyadmin.user;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.consts.Constants;
import com.easyadmin.service.SequenceService;
import com.easyadmin.service.SysService;
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
 * @author gongxinyi
 * @date 2017-11-08
 */
@Slf4j
@RestController
public class DataSourceController {
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;

    @GetMapping("/datasource/_datasource")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DataSource>> list() {
        List<DataSource> dataSources = sysService.getTenantDataStore().createQuery(DataSource.class).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", dataSources.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(dataSources);
    }

    @GetMapping("/datasource/_datasource/{datasourceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> findRole(@PathVariable("datasourceId") String datasourceId) {
        DataSource dataSource = sysService.getTenantDataStore().get(DataSource.class, datasourceId);
        return ResponseEntity.ok(dataSource);
    }

    @PostMapping("/datasource/_datasource")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> addDataSource(@RequestBody final DataSource dataSource) {
        dataSource.setId(sequenceService.getNextSequence(Constants.SYS_COL_DS + Constants._id).toString());
        sysService.getTenantDataStore().save(dataSource);
        return ResponseEntity.ok(dataSource);
    }

    @PutMapping(value = "/datasource/_datasource/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> editDataSource(@PathVariable("id") String id, @RequestBody DataSource dataSource) {
        sysService.getTenantDataStore().merge(dataSource);
        return ResponseEntity.ok(dataSource);
    }
}
