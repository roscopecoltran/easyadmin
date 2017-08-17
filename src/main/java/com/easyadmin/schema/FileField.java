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
public class FileField extends Field {
    private String fileType;


    @Builder(toBuilder = true)

    public FileField(String name, String label, Object defaultValue, Boolean required, String fileType) {
        super(name, Component.File, label, defaultValue, required);
        this.fileType = fileType;
    }
}
