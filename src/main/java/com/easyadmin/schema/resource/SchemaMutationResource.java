package com.easyadmin.schema.resource;

import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * schema mutation endpoints ,
 *
 * add field , change field type or label or somethings else
 *
 *
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@RestController
public class SchemaMutationResource {
    @RequestMapping(value = "/schemas", method = RequestMethod.POST)
    public ResponseEntity addField(Model model) {
        log.info("{}", JSON.serialize(model));
        return null;
    }
}
