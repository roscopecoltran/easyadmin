package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.LabelPosition;
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
public class ToggleField extends Field {
    private String label;
    private LabelPosition labelPosition;

    @Builder(toBuilder = true)
    private ToggleField(String name, String label, LabelPosition labelPosition) {
        super(name, Component.Toggle);
        this.label = label;
        this.labelPosition = labelPosition;
    }
}
