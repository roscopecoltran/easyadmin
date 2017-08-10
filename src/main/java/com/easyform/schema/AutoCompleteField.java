package com.easyform.schema;

import com.easyform.schema.enums.Component;
import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
public class AutoCompleteField extends Field {
    private DataSourceItem[] dataSource;

    @Builder(toBuilder = true)
    private AutoCompleteField(String name, Component component, DataSourceItem[] dataSource) {
        super(name, component);
        this.dataSource = dataSource;
    }
}
