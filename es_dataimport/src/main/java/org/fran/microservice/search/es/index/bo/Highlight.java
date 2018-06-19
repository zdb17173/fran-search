package org.fran.microservice.search.es.index.bo;

import java.io.Serializable;

/**
 * Created by fran on 2016/9/1.
 */
public class Highlight implements Serializable{
    String preTags;
    String postTags;

    public String getPreTags() {
        return preTags;
    }

    public void setPreTags(String preTags) {
        this.preTags = preTags;
    }

    public String getPostTags() {
        return postTags;
    }

    public void setPostTags(String postTags) {
        this.postTags = postTags;
    }
}
