package com.easyform.schema;

import lombok.Builder;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
@Builder
public class Item{
    private String name;
    private String value;
}