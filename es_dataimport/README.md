

## config

application.yaml
```
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/prodgateway?useUnicode=true&characterEncoding=utf8&autoReconnect=true
    username: root
    password: 1
    driver-class-name: com.mysql.jdbc.Driver
    testWhileIdle: true
    timeBetweenEvictionRunsMillis: 60000
    validationQuery: SELECT 1

esrest:
  cluster: test-cluster
  client_transport_ignore_cluster_name: false
  node_client: false
  prefix: newstest
  dateFormat:
  type: news
  servers:
    - port: 9200
      server: 127.0.0.1

es:
  cluster: test-cluster
  client_transport_ignore_cluster_name: false
  node_client: false
  prefix: newstest
  dateFormat:
  type: news
  servers:
    - port: 9300
      server: 127.0.0.1
    
```
