package com.easyadmin.schema;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.service.RdbService;
import com.easyadmin.service.SequenceService;
import com.easyadmin.service.SysService;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * schema query endpoints
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@RestController
public class SchemaController {
    @Resource
    SchemaService schemaService;
    @Autowired
    RdbService rdbService;
    @Autowired
    SysService sysService;
    @Autowired
    SequenceService sequenceService;

    @GetMapping("/schemas/_entitys")
    public ResponseEntity<List<Entity>> getSchemas() {
        List<Entity> entities = schemaService.findEntities();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", entities.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(entities);
    }

    @GetMapping("/schemas/_entitys/{eid}")
    public ResponseEntity<Entity> findOne(@PathVariable("eid") String eid) {
        Entity entity = schemaService.findOne(eid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @GetMapping("/schemas/_fields")
    public ResponseEntity<List<Field>> findAllFields(@RequestParam("eid") String eid) {
        List<Field> fields = schemaService.findEntities().stream().filter(entity -> entity.getId().equals(eid)).findFirst().get().getFields();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", fields.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(fields);
    }

    @GetMapping("/schemas/_fields/{fid}")
    public ResponseEntity<Field> findOneField(@PathVariable("fid") String fid) {
        Field field = schemaService.findOneField(fid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(field);
    }

    @PostMapping(value = "/schemas/_entitys")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entity> addEntity(@RequestBody Entity entity) {
        String id = sequenceService.getNextSequence(Constants.SYS_COL_Entity + "_id").toString();
        entity.setId(Constants.ENTITY_NAME_PREFIX + id);
        entity.setDataSourceId(Tenant.get().getCurrentDataSource().getId());
        sysService.getTenantDataStore().save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping(value = "/schemas/_entitys/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entity> editEntity(@PathVariable("id") String id, @RequestBody Entity entity) {
        entity.setFields(null);
        sysService.getTenantDataStore().merge(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PostMapping(value = "/schemas/_fields")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Field> addField(@RequestBody Field field) {
        String id = sequenceService.getNextSequence(Constants.SYS_COL_Field + "_id").toString();
        field.setId(Constants.FIELD_NAME_PREFIX + id);
        field.setName(field.getId());
        field.setDataSourceId(Tenant.get().getCurrentDataSource().getId());
        sysService.getTenantDataStore().save(field);
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @PutMapping(value = "/schemas/_fields/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Field> editField(@PathVariable("id") String id, @RequestBody Field field) {
        sysService.getTenantDataStore().merge(field);
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @PutMapping(value = "/schemas/sync/{dataSourceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity syncSchemas(@PathVariable("dataSourceId") String dataSourceId) {
        try {
            rdbService.syncSchemas(dataSourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/schemas/resetCurrentDs/{dataSourceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity resetCurrentDs(@PathVariable("dataSourceId") String dataSourceId) {
        final Query<DataSource> dataSourceIdQuery = sysService.getTenantDataStore().createQuery(DataSource.class).field("id").equal(dataSourceId);
        final UpdateOperations<DataSource> updateTrueOperations = sysService.getSysDataStore().createUpdateOperations(DataSource.class)
                .set("isCurrent", true);
        sysService.getTenantDataStore().update(dataSourceIdQuery, updateTrueOperations);
        final Query<DataSource> dataSourceNIdQuery = sysService.getTenantDataStore().createQuery(DataSource.class).field("id").notEqual(dataSourceId);
        final UpdateOperations<DataSource> updateFalseOperations = sysService.getSysDataStore().createUpdateOperations(DataSource.class)
                .set("isCurrent", false);
        sysService.getTenantDataStore().update(dataSourceNIdQuery, updateFalseOperations);
        return ResponseEntity.ok().build();
    }
}
