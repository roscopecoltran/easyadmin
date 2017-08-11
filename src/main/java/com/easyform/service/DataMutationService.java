package com.easyform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by gongxinyi on 2017-08-11.
 */
@Slf4j
@Controller
public class DataMutationService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/data/mutation", method = RequestMethod.POST)
    @SneakyThrows(JsonProcessingException.class)
    public void dataMutation(@RequestParam MultiValueMap<String, Object> params) {
        log.info("params:{}", new ObjectMapper().writeValueAsString(params));
    }
}
