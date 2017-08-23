package com.easyadmin.schema.resource;

import com.easyadmin.consts.Consts;
import com.easyadmin.schema.Entity;
import com.easyadmin.schema.field.Field;
import com.easyadmin.service.SchemaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * schema query endpoints
 * <p>
 * Created by gongxinyi on 2017-08-10.
 */
@RestController("schemas")
public class SchemaQueryResource {
    @Autowired
    SchemaQueryService schemaQueryService;

    @GetMapping("/entitys")
    public ResponseEntity<List<Entity>> list() {
        List<Entity> entities = schemaQueryService.findEntitys();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", entities.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(entities);
    }

    @GetMapping("/entitys/{entityId}")
    public ResponseEntity<Entity> findOne(@PathVariable("entityId")String entityId) {
        Entity entity = schemaQueryService.findOne(entityId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @GetMapping("/fields")
    public ResponseEntity<List<Field>> list(@RequestParam("entity") String entity) {
        List<Field> fields = schemaQueryService.findFields(entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", fields.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(fields);
    }
}
