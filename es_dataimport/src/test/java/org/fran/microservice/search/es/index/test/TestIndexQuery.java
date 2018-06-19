package org.fran.microservice.search.es.index.test;

import org.fran.microservice.search.es.index.bo.News;
import org.fran.microservice.search.es.index.bo.QueryCondition;
import org.fran.microservice.search.es.index.bo.Result;
import org.fran.microservice.search.es.index.service.highlevelrest.IndexQueryHighLevelRestService;
import org.fran.microservice.search.es.index.service.transport.IndexQueryTransportService;
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
    IndexQueryHighLevelRestService indexQueryHighLevelRestService;
    @Resource
    IndexQueryTransportService indexQueryTransportService;

    @Test
    public void test(){
        QueryCondition condition = new QueryCondition();
        condition.setKeyword("man");
        condition.setPage(0);
        condition.setSize(10);

        Result<List<News>> r = indexQueryHighLevelRestService.query(condition);
        System.out.println(r);

        Result<List<News>> rr = indexQueryTransportService.query(condition);
        System.out.println(rr);
    }
}
