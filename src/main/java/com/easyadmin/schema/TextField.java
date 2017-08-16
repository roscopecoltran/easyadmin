package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.InputType;
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
public class TextField extends Field {
    private InputType type;

    @Builder(toBuilder = true)
    private TextField(String name,InputType type) {
        super(name, Component.Text);
        this.type=type;
    }
}
