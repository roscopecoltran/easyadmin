package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by gongxinyi on 2017-08-16.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NullableBooleanField extends BooleanField {
    @Builder(toBuilder = true)
    private NullableBooleanField(String name, String label) {
        super(name, Component.NullableBoolean, label);
    }
}
