package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class RadioButtonGroupField extends Field {
    private Item[] items;

    @Builder(toBuilder = true)
    private RadioButtonGroupField(String name, Component component, Item[] items) {
        super(name, component);
        this.items = items;
    }
}
