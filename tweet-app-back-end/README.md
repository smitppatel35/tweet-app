# Tweet App
Component 2 Tweet App for Digital Honors Program

```shell script
zookeeper-server-start.bat .\config\zookeeper.properties
```

```shell script
kafka-server-start.bat .\config\server.properties
```

```shell script
kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic tweet-topic
```
