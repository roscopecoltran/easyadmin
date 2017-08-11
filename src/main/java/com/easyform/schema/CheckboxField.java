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
public class CheckboxField extends Field {
    private String label;

    @Builder(toBuilder = true)
    public CheckboxField(String name, String label) {
        super(name, Component.Checkbox);
        this.label = label;
    }
}
