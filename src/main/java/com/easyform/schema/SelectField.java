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
public class SelectField extends Field {
    private String floatingLabelText;
    private Item[] items;

    @Builder(toBuilder = true)
    private SelectField(String name, String floatingLabelText, Item[] items) {
        super(name, Component.SelectField);
        this.floatingLabelText = floatingLabelText;
        this.items = items;
    }
}
