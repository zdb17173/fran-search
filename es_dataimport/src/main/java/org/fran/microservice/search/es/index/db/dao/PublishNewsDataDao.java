package org.fran.microservice.search.es.index.db.dao;

import org.fran.microservice.search.es.index.db.po.PublishNewsData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fran on 2017/6/1.
 */
@Component
public class PublishNewsDataDao extends QueryTemplate<PublishNewsData> {
    public PublishNewsDataDao(JdbcTemplate template) {
        super(template);
    }

    public PublishNewsData selectById(long newsId){
        String sql = "select * from publish_news_data d where d.news_id= ? and d.type = 4";
        List<PublishNewsData> res = find(sql, new Object[]{newsId} );
        if(res == null || res.size() == 0){
            return null;
        }else
            return res.get(0);
    }
}
