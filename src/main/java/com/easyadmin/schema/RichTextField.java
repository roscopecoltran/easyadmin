package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongmark on 17/8/16.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RichTextField extends Field {
    public RichTextField(String name, String label, Object defaultValue, Boolean required) {
        super(name, Component.RichText, label, defaultValue, required);
    }
}
