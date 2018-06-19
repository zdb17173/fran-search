package org.fran.microservice.search.es.index.client;

/**
 * Created by fran on 2016/8/12.
 */
public class EsServer {
    int port;
    String server;
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
    }
}
