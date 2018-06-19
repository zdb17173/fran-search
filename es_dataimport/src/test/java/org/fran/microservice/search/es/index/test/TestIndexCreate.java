package org.fran.microservice.search.es.index.test;

import org.fran.microservice.search.es.index.service.highlevelrest.IndexBuildHighLevelRestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author fran
 * @Create date 2018/6/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestIndexCreate {
    @Resource
    IndexBuildHighLevelRestService indexBuildHighLevelRestService;

    @Test
    public void test(){

        indexBuildHighLevelRestService.indexAll();
    }
}
