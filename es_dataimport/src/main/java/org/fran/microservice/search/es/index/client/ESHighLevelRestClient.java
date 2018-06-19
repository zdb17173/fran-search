package org.fran.microservice.search.es.index.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fran
 * @Create date 2018/6/19
 */
@Configuration
@ConfigurationProperties("esrest")
public class ESHighLevelRestClient extends AbstractEsComponent{
    String cluster;
    List<EsServer> servers;
    RestHighLevelClient client = null;
    @PostConstruct
    private void init(){

        List<HttpHost> hosts = new ArrayList<>();

        for (EsServer server : servers) {
            hosts.add(new HttpHost(server.getServer(), server.getPort(), "http"));
        }

        client = new RestHighLevelClient(
                RestClient
                    .builder(hosts.toArray(new HttpHost[]{})));
    }

    public RestHighLevelClient getRestHighLevelClient(){
        return client;
    }


    public List<EsServer> getServers() {
        return servers;
    }

    public void setServers(List<EsServer> servers) {
        this.servers = servers;
    }
}
