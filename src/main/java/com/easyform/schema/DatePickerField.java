package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class DatePickerField extends Field {
    private String hintText;

    @Builder(toBuilder = true)
    public DatePickerField(String name, Component component, String hintText) {
        super(name, component);
        this.hintText = hintText;
    }
}
