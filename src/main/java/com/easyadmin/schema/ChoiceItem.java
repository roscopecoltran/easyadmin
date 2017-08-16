package com.easyadmin.schema;

import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@Builder
public class ChoiceItem {
    private String id;
    private String name;
}
