package org.fran.microservice.search.es.index.client;

/**
 * Created by fran on 2016/8/12.
 */

import org.elasticsearch.client.transport.TransportClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractEsComponent {

    private String prefix;
    private String dateFormat;
    private String type;

    public String getType() {
        return type;
    }

    public String getIndex() {

        if (dateFormat != null && !"".equals(dateFormat)) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            String date = format.format(new Date());
            return prefix + date;
        } else {
            return prefix;
        }

    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setType(String type) {
        this.type = type;
    }

}

