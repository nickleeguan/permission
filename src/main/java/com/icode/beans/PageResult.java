package com.icode.beans;

import com.google.common.collect.Lists;

import java.util.List;

public class PageResult<T> {
    private List<T> data = Lists.newArrayList();
    private int total = 0;

    public PageResult() {
    }

    public PageResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
