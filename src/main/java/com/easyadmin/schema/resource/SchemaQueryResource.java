package com.easyadmin.schema.resource;

import com.easyadmin.schema.Entity;
import com.easyadmin.schema.field.Field;
import com.easyadmin.service.SchemaQueryService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * schema query endpoints
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@RestController
public class SchemaQueryResource {
    @Autowired
    SchemaQueryService schemaQueryService;

    @GetMapping("/schemas/_entitys")
    public ResponseEntity<List<Entity>> list() {
        List<Entity> entities = schemaQueryService.findEntitys();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", entities.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(entities);
    }

    @GetMapping("/schemas/_entitys/{entityId}")
    public ResponseEntity<Entity> findOne(@PathVariable("entityId")String entityId) {
        Entity entity = schemaQueryService.findOne(entityId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @GetMapping("/schemas/_fields")
    public ResponseEntity<List<Field>> list(@RequestParam("entity") String entity) {
        List<Field> fields = schemaQueryService.findFields(entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", fields.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(fields);
    }

    @GetMapping("/schemas/_fields/{fieldId}")
    public ResponseEntity<Field> findOneField(@PathVariable("fieldId")String fieldId) {
        Field field= schemaQueryService.findOneField(fieldId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(field);
    }
}
