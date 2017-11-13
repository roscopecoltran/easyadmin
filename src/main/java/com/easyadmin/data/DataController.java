package com.easyadmin.data;

import com.easyadmin.consts.Constants;
import com.easyadmin.service.ServiceProxy;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class DataController {
    @Autowired
    ServiceProxy serviceProxy;

    @GetMapping(value = "/api/{entity}")
    @PreAuthorize("@securityService.hasProtectedAccess(#entity,'r')")
    public ResponseEntity<List<Map<String, Object>>> dataQuery(@PathVariable(Constants.ENTITY) String entity, @RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));
        List data = serviceProxy.getDataService().list(entity, allRequestParams);
        long count = serviceProxy.getDataService().count(entity, allRequestParams);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("X-Total-Count", count + "")
                .header("Access-Control-Expose-Headers", "X-Total-Count")
                .body(data);
    }

    @GetMapping(value = "/api/{entity}/{id}")
    @PreAuthorize("@securityService.hasProtectedAccess(#entity,'r')")
    public ResponseEntity<Map<String, Object>> findOne(@PathVariable(Constants.ENTITY) String entity, @PathVariable(Constants.id) String id, @RequestParam final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));
        Map<String, Object> object = serviceProxy.getDataService().findOne(entity, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(object);
    }

    @PostMapping(value = "/api/{entity}")
    @PreAuthorize("@securityService.hasProtectedAccess(#entity,'c')")
    public ResponseEntity<Map<String, Object>> dataMutation(@PathVariable(Constants.ENTITY) String entity, @RequestBody final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));

        Map<String, Object> document = serviceProxy.getDataService().save(entity, allRequestParams);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    @PutMapping(value = "/api/{entity}/{id}")
    @PreAuthorize("@securityService.hasProtectedAccess(#entity,'u')")
    public ResponseEntity<Map<String, Object>> dataMutation(@PathVariable(Constants.ENTITY) String entity, @PathVariable("id") String id, @RequestBody final Map<String, Object> allRequestParams) {
        log.info("params:{}", JSON.serialize(allRequestParams));

        Map<String, Object> document = serviceProxy.getDataService().update(entity, id, allRequestParams);
        return ResponseEntity.status(HttpStatus.OK).body(document);
    }

    @DeleteMapping(value = "/api/{entity}/{id}")
    @PreAuthorize("@securityService.hasProtectedAccess(#entity,'d')")
    public ResponseEntity<String> dataMutation(@PathVariable(Constants.ENTITY) String entity, @PathVariable("id") String id) {
        serviceProxy.getDataService().delete(entity, id);
        return ResponseEntity.ok(id);
    }
}
