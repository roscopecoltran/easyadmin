package com.easyform.service;

import com.easyform.schema.*;
import com.easyform.schema.enums.LabelPosition;
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
    @RequestMapping("/schemas")
    @ResponseBody
    public List<Field> schemas() {
        List<Field> fields = new ArrayList<>();
        fields.add(TextField.builder()
                .name("name")
                .floatingLabelText("Name")
                .defaultValue("11")
                .build());
        fields.add(SelectField.builder()
                .name("driver")
                .floatingLabelText("Driver")
                .items(new Item[]{
                        Item.builder().name("n1").value("v1").build(),
                        Item.builder().name("n2").value("v2").build()
                }).build());
        fields.add(ToggleField.builder()
                .name("toggle")
                .label("toggle")
                .labelPosition(LabelPosition.right)
                .build());
        fields.add(DatePickerField.builder()
                .name("when")
                .hintText("when")
                .build());
        fields.add(TimePickerField.builder()
                .name("at")
                .hintText("at")
                .build());
        fields.add(TextAreaField.builder()
                .name("mark")
                .defaultValue("11")
                .floatingLabelText("mark")
                .rows(1)
                .build());
        fields.add(SliderField.builder()
                .name("age")
                .defaultValue(18)
                .max(40)
                .step(2)
                .min(18)
                .build());
        fields.add(RadioButtonGroupField.builder()
                .name("gender")
                .items(new Item[]{
                        Item.builder().name("male").value("male").build(),
                        Item.builder().name("female").value("female").build()
                }).build());
        fields.add(CheckboxField.builder()
                .name("married")
                .label("married")
                .build());
        fields.add(AutoCompleteField.builder()
                .name("city")
                .dataSource(new DataSourceItem[]{
                        DataSourceItem.builder().id(1).name("wuhan").build(),
                        DataSourceItem.builder().id(2).name("chengdu").build()
                })
                .floatingLabelText("city")
                .build());
        return fields;
    }
}
