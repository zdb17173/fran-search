package org.fran.microservice.search.es.index.bo;

import java.io.Serializable;

/**
 * Created by fran on 2018/1/22.
 */
public class Sort implements Serializable {
    String field;//publishDate
    String order;//desc, asc

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
