package com.easyadmin.service;

import com.easyadmin.schema.*;
import com.easyadmin.schema.enums.InputType;
import com.easyadmin.schema.enums.LabelPosition;
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
public class SchemaQueryService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/schemas")
    @ResponseBody
    public List<Field> schemas() {
        List<Field> fields = new ArrayList<>();
        fields.add(TextField.builder()
                .name("name")
                .type(InputType.email)
                .required(true)
                .maxLength(20)
                .defaultValue("11")
                .build());
        fields.add(SelectField.builder()
                .name("driver")
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().name("n1").id("v1").build(),
                        ChoiceItem.builder().name("n2").id("v2").build()
                }).build());
        fields.add(BooleanField.builder()
                .name("toggle")
                .label("toggle")
                .build());
        fields.add(DateField.builder()
                .name("date")
                .label("出发日期")
                .build());
        fields.add(LongTextField.builder()
                .name("backup")
                .defaultValue("11")
                .build());
        fields.add(RadioButtonGroupField.builder()
                .name("gender")
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().name("男").id("male").build(),
                        ChoiceItem.builder().name("女").id("female").build()
                }).build());
        fields.add(CheckboxGroupField.builder()
                .name("married")
                .label("是否已婚")
                .build());
        fields.add(AutoCompleteField.builder()
                .name("city")
                .choices(new ChoiceItem[]{
                        ChoiceItem.builder().id("1").name("武汉").build(),
                        ChoiceItem.builder().id("2").name("成都").build()
                })
                .build());
        return fields;
    }
}
