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
public class RadioButtonGroupField extends Field {
    private Item[] items;

    @Builder(toBuilder = true)
    private RadioButtonGroupField(String name, Item[] items) {
        super(name, Component.RadioButtonGroup);
        this.items = items;
    }
}
