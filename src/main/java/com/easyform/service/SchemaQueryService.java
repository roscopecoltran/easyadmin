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
public class SchemaQueryService {
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/schemas")
    @ResponseBody
    public List<Field> schemas() {
        List<Field> fields = new ArrayList<>();
        fields.add(TextField.builder()
                .name("name")
                .email(true)
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
                .name("date")
                .hintText("出发日期")
                .build());
        fields.add(TimePickerField.builder()
                .name("time")
                .hintText("出发时间")
                .build());
        fields.add(TextAreaField.builder()
                .name("backup")
                .defaultValue("11")
                .floatingLabelText("备注")
                .rows(6)
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
                        Item.builder().name("男").value("male").build(),
                        Item.builder().name("女").value("female").build()
                }).build());
        fields.add(CheckboxField.builder()
                .name("married")
                .label("是否已婚")
                .build());
        fields.add(AutoCompleteField.builder()
                .name("city")
                .dataSource(new DataSourceItem[]{
                        DataSourceItem.builder().id(1).name("武汉").build(),
                        DataSourceItem.builder().id(2).name("成都").build()
                })
                .floatingLabelText("城市")
                .build());
        return fields;
    }
}
