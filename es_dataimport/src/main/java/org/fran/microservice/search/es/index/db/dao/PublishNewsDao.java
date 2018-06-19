package org.fran.microservice.search.es.index.db.dao;

import org.fran.microservice.search.es.index.db.po.PublishNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fran on 2016/8/15.
 */
@Component
public class PublishNewsDao extends QueryTemplate<PublishNews> {

    @Autowired
    public PublishNewsDao(JdbcTemplate template) {
        super(template);
    }

    public List<PublishNews> findByStatus(int status, Pageable pageable){
        String sql = "SELECT n.* , u.nameEN AS editor1_name FROM publish_news n LEFT JOIN auth_users u ON n.`editor1` = u.`id` WHERE n.status = ?";

        List<PublishNews> res = findByPage(sql, new Object[]{status}, pageable);
        return res;
    }

    public PublishNews findById(long id){
        String sql = "SELECT n.* , u.nameEN AS editor1_name FROM publish_news n LEFT JOIN auth_users u ON n.`editor1` = u.`id` WHERE n.status = 0 AND  n.id = ? ";

        return findByPrimaryKey(sql, new Object[]{id});
    }
}
