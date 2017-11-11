package com.easyadmin.schema.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by gongxinyi on 2017-11-11.
 */
@Data
@AllArgsConstructor
public class ChoiceItemNum implements IChoiceItem {
    private Long id;
    private String name;
}
