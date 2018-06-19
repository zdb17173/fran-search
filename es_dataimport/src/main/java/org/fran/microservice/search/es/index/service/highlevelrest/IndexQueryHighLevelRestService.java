package org.fran.microservice.search.es.index.service.highlevelrest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.fran.microservice.search.es.index.bo.*;
import org.fran.microservice.search.es.index.client.ESHighLevelRestClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author fran
 * @Create date 2018/6/19
 */
@Service
public class IndexQueryHighLevelRestService {
    @Resource
    ESHighLevelRestClient eSHighLevelRestClient;

    public Result<List<News>> query(QueryCondition condition) {

        try {
            BoolQueryBuilder query = QueryBuilders.boolQuery();

            query = query
                    .must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("headline", condition.getKeyword()))
                            .should(QueryBuilders.matchQuery("newsDetail", condition.getKeyword()))
                            .should(QueryBuilders.matchQuery("content", condition.getKeyword()))
                    )
                    .mustNot(QueryBuilders.termQuery("newsType", 1));

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(query);
            sourceBuilder.from(condition.getPage() * condition.getSize());
            sourceBuilder.size(condition.getSize());
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

            if(condition.getSort()!= null){
                Sort sort = condition.getSort();
                SortOrder order = null;
                if(sort.getOrder()!= null){
                    if(sort.getOrder().equals("desc"))
                        order = SortOrder.DESC;
                    if(sort.getOrder().equals("asc"))
                        order = SortOrder.ASC;
                }
                if(order != null && sort.getField()!= null)
                    sourceBuilder.sort(SortBuilders.fieldSort(sort.getField()).order(SortOrder.DESC));
            }


            if(condition.getHighlight()!= null){
                Highlight highlight = condition.getHighlight();
                if( highlight.getPostTags() == null
                        || highlight.getPreTags() == null){

                }else{
                    HighlightBuilder highlightBuilder = new HighlightBuilder()
                            .field("headline")
                            .field("newsDetail")
                            .preTags(highlight.getPreTags())
                            .postTags(highlight.getPostTags());

                    sourceBuilder.highlighter(highlightBuilder);
                }
            }


            RestHighLevelClient client = eSHighLevelRestClient.getRestHighLevelClient();

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.source(sourceBuilder);

            SearchResponse res = client.search(searchRequest);
            SearchHits hits = res.getHits();
            long total = hits.getTotalHits();
            SearchHit[] hit = hits.getHits();

            ObjectMapper mapper = new ObjectMapper();

            List<News> listRes = new ArrayList<>();
            for(SearchHit h : hit){

                String s = h.getSourceAsString();
                try {
                    News vo = mapper.readValue(s, News.class);

                    highLightHandle(h, vo);

                    listRes.add(vo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Result r = new Result();
            r.setTotal(Long.valueOf(total).intValue());
            r.setStatus(200);
            r.setCurr(condition.getPage());
            r.setData(listRes);
            return r;
        }catch (Exception e){

        }

        return null;
    }


    private void highLightHandle(SearchHit h, News vo){
        Map<String, HighlightField> result = h.getHighlightFields();

        String newHeadline = getHighLightText(result.get("headline"));
        if(newHeadline != null)
            vo.setHeadline(newHeadline);
        String newDetail = getHighLightText(result.get("newsDetail"));
        if(newDetail != null)
            vo.setNewsDetail(newDetail);
    }

    private String getHighLightText(HighlightField f){
        if(f == null || f.fragments() == null || f.fragments().length == 0)
            return null;

        String t = "";
        for(Text text : f.fragments()){
            t += text;
        }
        return t;
    }
}
