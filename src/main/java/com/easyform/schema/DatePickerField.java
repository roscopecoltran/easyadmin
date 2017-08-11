package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DatePickerField extends Field {
    private String hintText;

    @Builder(toBuilder = true)
    public DatePickerField(String name, String hintText) {
        super(name, Component.DatePicker);
        this.hintText = hintText;
    }
}
