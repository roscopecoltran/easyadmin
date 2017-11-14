package com.easyadmin.data;

import lombok.Data;

/**
 * Created by gongxinyi on 2017-08-19.
 */
public class RequestScope {
    private String _sort;
    private String _order;
    private int _start;
    private int _end;

    public String get_sort() {
        return _sort;
    }

    public RequestScope set_sort(String _sort) {
        this._sort = _sort;
        return this;
    }

    public String get_order() {
        return _order;
    }

    public RequestScope set_order(String _order) {
        this._order = _order;
        return this;
    }

    public int get_start() {
        return _start;
    }

    public RequestScope set_start(int _start) {
        this._start = _start;
        return this;
    }

    public int get_end() {
        return _end;
    }

    public RequestScope set_end(int _end) {
        this._end = _end;
        return this;
    }

    public int getLimit() {
        return _end - _start;
    }

}
