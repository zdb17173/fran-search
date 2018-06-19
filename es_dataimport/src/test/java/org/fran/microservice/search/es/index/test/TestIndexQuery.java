package org.fran.microservice.search.es.index.test;

import org.fran.microservice.search.es.index.bo.News;
import org.fran.microservice.search.es.index.bo.QueryCondition;
import org.fran.microservice.search.es.index.bo.Result;
import org.fran.microservice.search.es.index.service.transport.IndexQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author fran
 * @Create date 2018/6/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestIndexQuery {
    @Resource
    IndexQueryService indexQueryService;

    @Test
    public void test(){
        QueryCondition condition = new QueryCondition();
        condition.setKeyword("man");

        Result<List<News>> r = indexQueryService.query(condition);
        System.out.println(r);
    }
}
