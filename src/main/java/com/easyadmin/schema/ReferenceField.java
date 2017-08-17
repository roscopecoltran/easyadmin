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
public class ReferenceField extends Field {
    private String reference;
    private String referenceOptionText;

    @Builder
    public ReferenceField(String name, String label, Object defaultValue, Boolean required, String reference, String referenceOptionText) {
        super(name, Component.Reference, label, defaultValue, required);
        this.reference = reference;
        this.referenceOptionText = referenceOptionText;
    }
}
