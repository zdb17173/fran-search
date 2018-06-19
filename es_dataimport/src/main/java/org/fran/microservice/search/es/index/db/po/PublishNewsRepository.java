package org.fran.microservice.search.es.index.db.po;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by fran on 2016/8/12.
 */
public interface PublishNewsRepository extends Repository<PublishNews, Long> {

    Page<PublishNews> findAll(Pageable pageable);
    Page<PublishNews> findByStatus(Integer status, Pageable pageable);
}
