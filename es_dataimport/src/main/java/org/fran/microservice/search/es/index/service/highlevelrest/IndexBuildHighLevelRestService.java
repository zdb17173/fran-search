package org.fran.microservice.search.es.index.service.highlevelrest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.fran.microservice.search.es.index.bo.Result;
import org.fran.microservice.search.es.index.client.ESHighLevelRestClient;
import org.fran.microservice.search.es.index.db.dao.PublishNewsDao;
import org.fran.microservice.search.es.index.db.dao.PublishNewsDataDao;
import org.fran.microservice.search.es.index.db.dao.PublishNewsSectionDao;
import org.fran.microservice.search.es.index.db.po.Condition;
import org.fran.microservice.search.es.index.db.po.NewsSection;
import org.fran.microservice.search.es.index.db.po.PublishNews;
import org.fran.microservice.search.es.index.db.po.PublishNewsData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author fran
 * @Create date 2018/6/18
 */
@Service
public class IndexBuildHighLevelRestService {
    @Resource
    ESHighLevelRestClient eSHighLevelRestClient;
    @Resource
    PublishNewsDao publishNewsDao;
    @Resource
    PublishNewsDataDao publishNewsDataDao;
    @Resource
    PublishNewsSectionDao publishNewsSectionDao;

    public Result indexAll() {
        Result res = new Result();
        try{
            int pageSize = 10;
            Condition condition = new Condition();
            ObjectMapper mapper = new ObjectMapper();
            for(int i= 0 ; i< 10000; i ++){
                condition.setOffset(i*pageSize);
                condition.setPageNumber(i);
                condition.setPageSize(pageSize);

                List<PublishNews> list = publishNewsDao.findByStatus(0, condition);

                if(list!= null && list.size() > 0){
                    List<PublishNews> newsList = list;
                    for(PublishNews news : newsList){

                        PublishNewsData newsData = publishNewsDataDao.selectById(news.getInstanceId());
                        try{
                            if(newsData != null && newsData.getContent()!= null)
                                news.setContent(getAllContents(new String(newsData.getContent(), "UTF-8")));
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        List<NewsSection> sections = new ArrayList<>();
                        sections.add(new NewsSection(1,"section1"));
                        sections.add(new NewsSection(2,"sectionFran"));
                        news.setSections(sections);

                        try {
//                            byte[] json = mapper.writeValueAsBytes(news);
                            String json = mapper.writeValueAsString(news);
                            RestHighLevelClient client = eSHighLevelRestClient.getRestHighLevelClient();

                            IndexRequest request = new IndexRequest(
                                    eSHighLevelRestClient.getIndex(),
                                    eSHighLevelRestClient.getType(),
                                    news.getId().toString())
                                    .source(json, XContentType.JSON);

                            IndexResponse response = client.index(request);

                            System.out.println(response.toString());
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    break;
                }
            }
            res.setStatus(200);
        }catch (Exception e){
            e.printStackTrace();
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }

        return res;
    }

    private String getAllContents(String contents){

        if(contents == null || contents.equals(""))
            return contents;

        String content = "";
        String res = "";
        ObjectMapper mapper = new ObjectMapper();
        try {

            JsonNode node = mapper.readTree(contents);
            Iterator<JsonNode> elements = node.elements();
            while(elements.hasNext()){
                JsonNode n = elements.next();
                int contentType = n.get("contentType").intValue();
                switch (contentType){
                    case 1:
                        content = n.get("content").textValue();
                        content =  replaceAllHtml(content);
                        if(content == null || content.equals(""))
                            continue;
                        else {
                            res += content + "\r\n";
                            continue;
                        }
                    case 2:
                        content = n.get("content").textValue();
                        content =  replaceAllHtml(content);
                        if(content == null || content.equals(""))
                            continue;
                        else{
                            res += content + "\r\n";
                            continue;
                        }
                    case 9:
                        content = n.get("content").textValue();
                        content =  replaceAllHtml(content);
                        if(content == null || content.equals(""))
                            continue;
                        else
                            res += content;
                    case 10:
                        content = n.get("content").textValue();
                        content =  replaceAllHtml(content);
                        if(content == null || content.equals(""))
                            continue;
                        else {
                            res += content + "\r\n";
                            continue;
                        }
                    case 11:
                        content = n.get("content").textValue();
                        content =  replaceAllHtml(content);
                        if(content == null || content.equals(""))
                            continue;
                        else {
                            res += content + "\r\n";
                            continue;
                        }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    private static String replaceAllHtml(String text){
        if(text == null || "".equals(text))
            return text;
        else{
            try{
                return text.replaceAll("<[^>]*>", "").replace("\"", "");
            }catch (Exception e){
                (new RuntimeException("replaceAllHtmlError["+ text +"]", e)).printStackTrace();
                return "";
            }
        }
    }
}
