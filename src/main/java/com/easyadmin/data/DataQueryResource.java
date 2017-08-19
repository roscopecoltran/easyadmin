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
 * 数据查询endpoints，spring mvc的实现
 * <p>
 * json 标准遵循 json server
 * <p>
 * https://github.com/typicode/json-server
 * <p>
 * <p>
 * REST verb	API calls
 * GET_LIST	GET http://my.api.url/posts?sort=['title','ASC']&range=[0, 24]&filter={title:'bar'}
 * GET_ONE	GET http://my.api.url/posts/123
 * CREATE	POST http://my.api.url/posts/123
 * UPDATE	PUT http://my.api.url/posts/123
 * DELETE	DELETE http://my.api.url/posts/123
 * GET_MANY	GET http://my.api.url/posts?filter={ids:[123,456,789]}
 * GET_MANY_REFERENCE	GET http://my.api.url/posts?filter={author_id:345}
 * <p>
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
        List data = dataQueryService.list(entity,allRequestParams);
        long count = dataQueryService.count(entity,allRequestParams);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", count + "")
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
