package com.easyadmin.data;

import com.easyadmin.consts.Consts;
import com.easyadmin.service.DataQueryService;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class DataQueryResource {
    @Autowired
    DataQueryService dataQueryService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{entity}", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> dataQuery(@PathVariable(Consts.ENTITY) String entity, @RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));
        List data = dataQueryService.list(entity);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", data.size() + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(data);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/api/{entity}/{id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> findOne(@PathVariable(Consts.ENTITY) String entity, @PathVariable(Consts.KEY) String id, @RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));
        Map<String, Object> object = dataQueryService.findOne(entity, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(object);
    }


}
