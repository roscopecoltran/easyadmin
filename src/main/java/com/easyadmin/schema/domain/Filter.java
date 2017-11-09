package com.easyadmin.schema.domain;

import com.easyadmin.schema.enums.OperatorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author gongxinyi
 * @date 2017-11-09
 */
@Data
@AllArgsConstructor
public class Filter {
    private String key;
    private OperatorEnum operatorEnum;
    private Object value;
}
