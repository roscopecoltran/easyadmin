package com.easyadmin.data;

import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-19.
 */
@Data
public class RequestScope {
    private String _sort;
    private String _order;
    private int _start;
    private int _end;

    public int getLimit(){
        return _end-_start;
    }

}
