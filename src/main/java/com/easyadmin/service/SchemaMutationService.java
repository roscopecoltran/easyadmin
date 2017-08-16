package com.easyadmin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Controller
public class SchemaMutationService {
    @RequestMapping(value = "/schemas",method = RequestMethod.POST)
    @SneakyThrows(JsonProcessingException.class)
    public ResponseEntity addField(Model model) {
        log.info(new ObjectMapper().writeValueAsString(model));
        return null;
    }
}
