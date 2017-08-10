package com.easyform.service;

import com.easyform.schema.Field;
import com.easyform.schema.Item;
import com.easyform.schema.SelectField;
import com.easyform.schema.TextField;
import com.easyform.schema.enums.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Controller
public class SchemaService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/schemas")
    @ResponseBody
    public List<Field> schemas() {
        List<Field> fields = new ArrayList<>();
        fields.add(TextField.builder()
                .name("name")
                .floatingLabelText("Name")
                .component(Component.TextField)
                .build());
        fields.add(SelectField.builder()
                .name("driver")
                .floatingLabelText("Driver")
                .component(Component.SelectField)
                .items(new Item[]{
                        Item.builder().name("n1").value("v1").build(),
                        Item.builder().name("n2").value("v2").build()
                }).build());
        return fields;
    }
}
