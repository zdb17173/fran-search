package org.fran.microservice.search.es.index.bo;

import java.io.Serializable;

/**
 * Created by fran on 2016/8/12.
 */
public class Result<T> implements Serializable{
    private T data;
    private int status;
    private String message;
    private int curr;
    private int total;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
