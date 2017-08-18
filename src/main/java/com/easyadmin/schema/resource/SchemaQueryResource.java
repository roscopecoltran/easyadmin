package com.easyadmin.schema.resource;

import com.easyadmin.schema.field.Field;
import com.easyadmin.service.SchemaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * schema query endpoints
 *
 * Created by gongxinyi on 2017-08-10.
 */
@Controller
public class SchemaQueryResource {
    @Autowired
    SchemaQueryService schemaQueryService;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/schemas")
    @ResponseBody
    public List<Field> list() {
        return schemaQueryService.list();
    }
}
