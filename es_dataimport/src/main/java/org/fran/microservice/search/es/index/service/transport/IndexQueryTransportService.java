package org.fran.microservice.search.es.index.service.transport;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.fran.microservice.search.es.index.bo.*;
import org.fran.microservice.search.es.index.client.AbstractEsComponent;
import org.fran.microservice.search.es.index.client.EsComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author fran
 * @Create date 2018/6/18
 */
@Service
public class IndexQueryTransportService {
    @Resource
    EsComponent esComponent;

    public Result<List<News>> query(QueryCondition condition) {
        try{
            BoolQueryBuilder query = QueryBuilders.boolQuery();

//            query = query
//                .should(QueryBuilders.termQuery("headline", condition.getKeyword()))
//                .should(QueryBuilders.termQuery("newsDetail", condition.getKeyword()))
//                .should(QueryBuilders.termQuery("editor1Name", condition.getKeyword()))
//                .should(QueryBuilders.termQuery("city", condition.getKeyword()));

            query = query
                    .must(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("headline", condition.getKeyword()))
                            .should(QueryBuilders.matchQuery("newsDetail", condition.getKeyword()))
                            .should(QueryBuilders.matchQuery("content", condition.getKeyword()))
                    )
                    .mustNot(QueryBuilders.termQuery("newsType",1));

//                    .should(QueryBuilders.matchQuery("editor1Name", condition.getKeyword()))
//                    .should(QueryBuilders.matchQuery("city", condition.getKeyword()));

//            query = query.must(QueryBuilders.queryStringQuery(condition.getKeyword()).defaultField("_all"));

//            System.out.println(query.toString());

            TransportClient client = esComponent.getTransportClient();

            SearchRequestBuilder request = client
                    .prepareSearch(esComponent.getIndex())
                    .setTypes(esComponent.getType())
                    .setQuery(query)
                    .setSize(condition.getSize())
                    .setFrom(condition.getPage() * condition.getSize());

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
                    request.addSort(SortBuilders.fieldSort(sort.getField()).order(SortOrder.DESC));
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

                    request.highlighter(highlightBuilder);
                }
            }

//            System.out.println(request.toString());
            SearchResponse res = request.get();

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
            Result r = new Result();
            r.setStatus(500);
            r.setCurr(condition.getPage());
            r.setMessage(e.getMessage());
            e.printStackTrace();
            return r;
        }
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
