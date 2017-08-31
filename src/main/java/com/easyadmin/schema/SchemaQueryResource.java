package com.easyadmin.schema;

import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.service.SchemaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/schemas/_entitys/{eid}")
    public ResponseEntity<Entity> findOne(@PathVariable("eid") String eid) {
        Entity entity = schemaQueryService.findOne(eid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @GetMapping("/schemas/_fields")
    public ResponseEntity<List<Field>> list(@RequestParam("eid") String eid) {
        List<Field> fields = schemaQueryService.findFields(eid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", fields.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(fields);
    }

    @GetMapping("/schemas/_fields/{fid}")
    public ResponseEntity<Field> findOneField(@PathVariable("fid") String fid) {
        Field field = schemaQueryService.findOneField(fid);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(field);
    }
}
