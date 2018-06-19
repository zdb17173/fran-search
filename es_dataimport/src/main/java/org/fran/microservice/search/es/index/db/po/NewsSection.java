package org.fran.microservice.search.es.index.db.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by fran on 2017/6/13.
 */
@Entity
public class NewsSection implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    @Column(name="news_id")
    private long newsId;
    @Column(name="section_id")
    private long sectionId;
    @Column(name="section_name")
    private String sectionName;

    public NewsSection(){}

    public NewsSection(long id, String sectionName){this.id = id; this.sectionName = sectionName;}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
}
