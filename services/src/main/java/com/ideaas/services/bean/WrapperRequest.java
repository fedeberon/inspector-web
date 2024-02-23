package com.ideaas.services.bean;

import org.springframework.stereotype.Component;

@Component
public class WrapperRequest <T>{

    private T myObject;

    private Integer page = 0;

    public WrapperRequest() {

    }

    public WrapperRequest(T myObject, Integer page) {
        this.myObject = myObject;
        this.page = page;
    }

    public T getMyObject() {
        return myObject;
    }

    public void setMyObject(T myObject) {
        this.myObject = myObject;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
