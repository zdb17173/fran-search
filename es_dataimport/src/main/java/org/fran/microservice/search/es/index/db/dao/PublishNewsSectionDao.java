package org.fran.microservice.search.es.index.db.dao;

import org.fran.microservice.search.es.index.db.po.NewsSection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fran on 2017/6/13.
 */
@Component
public class PublishNewsSectionDao extends QueryTemplate<NewsSection> {
    public PublishNewsSectionDao(JdbcTemplate template) {
        super(template);
    }

    public List<NewsSection> selectById(long newsId){
        String sql = "SELECT s.*, l.`name` AS section_name FROM publish_news_section s JOIN resource_section l ON s.`section_id` = l.`id` WHERE s.`news_id` =  ?";
        List<NewsSection> res = find(sql, new Object[]{newsId} );
        return res;

    }

}
