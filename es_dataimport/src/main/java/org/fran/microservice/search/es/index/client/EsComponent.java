package org.fran.microservice.search.es.index.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by fran on 2016/8/12.
 */
@Configuration
@ConfigurationProperties("es")
public class EsComponent extends AbstractEsComponent{
    String cluster;
    boolean client_transport_ignore_cluster_name;
    boolean node_client;
    List<EsServer> servers;
    protected TransportClient elasticClient;

    @PostConstruct
    private void init(){
        try {
            TransportClient build = null;
            if (cluster != null) {
                Settings settings = Settings.builder()
                        .put("cluster.name", cluster)
//                        .put("client.transport.ignore_cluster_name", false)
//				        .put("node.client", true)
//				        .put("client.transport.sniff", true)
                        .build();
                build = new PreBuiltTransportClient(settings);

            } else
                build = new PreBuiltTransportClient(Settings.EMPTY);

            if (servers.size() > 1) {
                for (EsServer server : servers) {
                    build = build.addTransportAddress(new TransportAddress(InetAddress.getByName(server.getServer()), server.getPort()));
                }
            } else {
                EsServer server = servers.get(0);
                build = build.addTransportAddress(new TransportAddress(InetAddress.getByName(server.getServer()), server.getPort()));
            }

            elasticClient = build;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        System.out.println("startUp");
    }


    public TransportClient getTransportClient(){
        return elasticClient;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public boolean isClient_transport_ignore_cluster_name() {
        return client_transport_ignore_cluster_name;
    }

    public void setClient_transport_ignore_cluster_name(boolean client_transport_ignore_cluster_name) {
        this.client_transport_ignore_cluster_name = client_transport_ignore_cluster_name;
    }

    public boolean isNode_client() {
        return node_client;
    }

    public void setNode_client(boolean node_client) {
        this.node_client = node_client;
    }

    public List<EsServer> getServers() {
        return servers;
    }

    public void setServers(List<EsServer> servers) {
        this.servers = servers;
    }


}
