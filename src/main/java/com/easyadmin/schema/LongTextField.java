package com.easyadmin.schema;

import com.easyadmin.schema.enums.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongmark on 17/8/16.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LongTextField extends Field {

    @Builder(toBuilder = true)
    public LongTextField(String name, String label, Object defaultValue, Boolean required) {
        super(name, Component.LongText, label, defaultValue, required);
    }
}
