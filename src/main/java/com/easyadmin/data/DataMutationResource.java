package com.easyadmin.data;

import com.easyadmin.consts.Consts;
import com.easyadmin.service.DataMutationService;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class DataMutationResource {
    @Autowired
    DataMutationService dataMutationService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{entity}", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> dataMutation(@PathVariable(Consts.ENTITY) String entity, @RequestBody final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));

        Document document = dataMutationService.save(entity, allRequestParams);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{entity}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> dataMutation(@PathVariable("table") String table, @PathVariable("id") String id, @RequestBody final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));

        Document document = dataMutationService.update(table, id, allRequestParams);
        return ResponseEntity.status(HttpStatus.OK).body(document);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{entity}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> dataMutation(@PathVariable("table") String table, @PathVariable("id") String id) {
        dataMutationService.deleteLogic(table, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
