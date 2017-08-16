package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
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
public class TimePickerField extends Field {
    private String hintText;

    @Builder(toBuilder = true)
    public TimePickerField(String name, String hintText) {
        super(name, Component.TimePicker);
        this.hintText = hintText;
    }
}
