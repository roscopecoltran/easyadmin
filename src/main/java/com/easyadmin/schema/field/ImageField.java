package com.easyadmin.schema.field;

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
public class ImageField extends Field {

    @Builder(toBuilder = true)
    public ImageField(String name, String label, Object defaultValue, Boolean required) {
        super(name, Component.Image, label, defaultValue, required);
    }
}
