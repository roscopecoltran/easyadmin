package com.easyadmin.user;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.consts.Constants;
import com.easyadmin.security.security.Role;
import com.easyadmin.service.MongoDbService;
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
 * @author gongxinyi
 * @date 2017-11-08
 */
@Slf4j
@RestController
public class DataSourceController {
    @Autowired
    MongoDbService mongoDbService;
    @Autowired
    SequenceService sequenceService;

    @GetMapping("/datasource/_datasource")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DataSource>> list() {
        List<DataSource> dataSources = mongoDbService.getDataStore().createQuery(DataSource.class).asList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", dataSources.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(dataSources);
    }

    @GetMapping("/datasource/_datasource/{datasourceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> findRole(@PathVariable("datasourceId") String datasourceId) {
        DataSource dataSource = mongoDbService.getDataStore().get(DataSource.class, datasourceId);
        return ResponseEntity.ok(dataSource);
    }

    @PostMapping("/datasource/_datasource")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> addDataSource(@RequestBody final DataSource dataSource) {
        dataSource.setId(sequenceService.getNextSequence(Constants.SYS_COL_DS + Constants._id).toString());
        mongoDbService.getDataStore().save(dataSource);
        return ResponseEntity.ok(dataSource);
    }

    @PutMapping(value = "/datasource/_datasource/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DataSource> editDataSource(@PathVariable("id") String id, @RequestBody DataSource dataSource) {
        final Query<DataSource> dataSourceQuery = mongoDbService.getDataStore().createQuery(DataSource.class).field("id").equal(id);
        final UpdateOperations<DataSource> updateOperations = mongoDbService.getDataStore().createUpdateOperations(DataSource.class)
                .set("jdbcUrl", dataSource.getJdbcUrl())
                .set("username", dataSource.getUsername())
                .set("password", dataSource.getPassword())
                .set("type", dataSource.getType())
                .set("enabled", dataSource.isEnabled());

        mongoDbService.getDataStore().update(dataSourceQuery, updateOperations);
        return ResponseEntity.ok(dataSource);
    }
}
