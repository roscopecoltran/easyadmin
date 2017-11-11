package com.easyadmin.schema.domain;

import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-10.
 */
@Data
public class ChoiceItem implements IChoiceItem {
    private String id;
    private String name;

    public ChoiceItemNum toChoiceItemNum() {
        return new ChoiceItemNum(Long.parseLong(this.id), this.name);
    }
}
