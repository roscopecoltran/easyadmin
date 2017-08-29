package com.easyadmin.schema;

import com.easyadmin.consts.Consts;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.service.DataMutationService;
import com.easyadmin.service.SequenceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * schema mutation endpoints ,
 * <p>
 * add domain , change domain type or label or somethings else
 * <p>
 * <p>
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class SchemaMutationResource {
    @Autowired
    DataMutationService dataMutationService;


    @PostMapping(value = "/schemas/_entitys")
    public ResponseEntity<Entity> addEntity(@RequestBody Entity entity) {
        String id = SequenceUtil.getNextSequence(Consts.SYS_COL_Entity+"_id").toString();
        entity.setId("e" + id);
        dataMutationService.save(Consts.SYS_COL_Entity, new ObjectMapper().convertValue(entity, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping(value = "/schemas/_entitys/{id}")
    public ResponseEntity<Entity> editEntity(@PathVariable("id") String id, @RequestBody Entity entity) {
        dataMutationService.update(Consts.SYS_COL_Entity, id, new ObjectMapper().convertValue(entity, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PostMapping(value = "/schemas/_fields")
    public ResponseEntity<Field> addField(@RequestBody Field field) {
        String id = SequenceUtil.getNextSequence(Consts.SYS_COL_Field+"_id").toString();
        field.setId("f" + id);
        dataMutationService.save(Consts.SYS_COL_Field, new ObjectMapper().convertValue(field, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }

    @PutMapping(value = "/schemas/_fields/{id}")
    public ResponseEntity<Field> editField(@PathVariable("id") String id, @RequestBody Field field) {
        dataMutationService.update(Consts.SYS_COL_Field, id, new ObjectMapper().convertValue(field, Map.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(field);
    }
}