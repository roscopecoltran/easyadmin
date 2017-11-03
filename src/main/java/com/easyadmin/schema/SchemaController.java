package com.easyadmin.schema;

import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * schema query endpoints
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@RestController
public class SchemaController {
    @Resource
    SchemaService schemaService;
    @Resource(name="dataDbService")
    DataService dataService;
    @Autowired
    RdbService rdbService;
    @Autowired
    MongoDbService mongoDbService;
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
        List<Field> fields = schemaService.findFields(eid);
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
        mongoDbService.getDataStore().save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping(value = "/schemas/_entitys/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Entity> editEntity(@PathVariable("id") String id, @RequestBody Entity entity) {
        dataService.update(Constants.SYS_COL_Entity, id, new ObjectMapper().convertValue(entity, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PostMapping(value = "/schemas/_fields")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Field> addField(@RequestBody Field field) {
        String id = sequenceService.getNextSequence(Constants.SYS_COL_Field + "_id").toString();
        field.setId(Constants.FIELD_NAME_PREFIX + id);
        mongoDbService.getDataStore().save(field);
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @PutMapping(value = "/schemas/_fields/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Field> editField(@PathVariable("id") String id, @RequestBody Field field) {
        dataService.update(Constants.SYS_COL_Field, id, new ObjectMapper().convertValue(field, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @GetMapping(value = "/schemas/sync")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity syncSchemas() {
        try {
            rdbService.syncSchemas(rdbService.getSchema());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
