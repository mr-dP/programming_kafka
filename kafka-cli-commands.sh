-> when we create a topic, we have to specify the number of partitions and number of replication-factor
-> you cannot create topic with a replication-factor greater than the number of brokers you have

kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --create --partitions 3 --replication-factor 1

kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --list

kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --describe
Topic: first_topic      PartitionCount: 3       ReplicationFactor: 1    Configs: segment.bytes=1073741824
        Topic: first_topic      Partition: 0    Leader: 0       Replicas: 0     Isr: 0
        Topic: first_topic      Partition: 1    Leader: 0       Replicas: 0     Isr: 0
        Topic: first_topic      Partition: 2    Leader: 0       Replicas: 0     Isr: 0

kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --delete


kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic first_topic

kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic first_topic --producer-property acks=all

kafka-console-producer.bat --broker-list 127.0.0.1:9092 --topic another_topic
>this topic is new
[2020-09-01 10:39:17,139] WARN [Producer clientId=console-producer] Error while fetching metadata with correlation id 3 : {another_topic=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
[2020-09-01 10:39:17,260] WARN [Producer clientId=console-producer] Error while fetching metadata with correlation id 4 : {another_topic=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
>next message

[2020-09-01 10:39:17,131] INFO [KafkaApi-0] Auto creation of topic another_topic with 1 partitions and replication factor 1 is successful (kafka.server.KafkaApis)

-> The new topic will be created and somehow producing to topic that did not exist before created it
-> Because the topics get created but there was no Leader election happended yet, so we get the exception "LEADER_NOT_AVAILABLE"

kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --topic another_topic --describe
Topic: another_topic    PartitionCount: 1       ReplicationFactor: 1    Configs: segment.bytes=1073741824
        Topic: another_topic    Partition: 0    Leader: 0       Replicas: 0     Isr: 0

-> Always create a Topic before producing to it otherwise it will get created with some defaults

-> server.properties => num.partitions=3
-> If the topic does not exist this will create it with 3 Partitions by default


kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic

kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning

kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --group first_application

kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic --group second_application --from-beginning


kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --list

kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --describe --group second_application
GROUP              TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID     HOST            CLIENT-ID
second_application first_topic     0          26              26              0               -               -               -
second_application first_topic     1          20              20              0               -               -               -
second_application first_topic     2          19              19              0               -               -               -


kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --group first_application --reset-offsets --to-earliest --execute --topic first_topic
GROUP                          TOPIC                          PARTITION  NEW-OFFSET
first_application              first_topic                    0          0
first_application              first_topic                    1          0
first_application              first_topic                    2          0

kafka-consumer-groups.bat --bootstrap-server 127.0.0.1:9092 --group first_application --reset-offsets --shift-by -2 --execute --topic first_topic
GROUP                          TOPIC                          PARTITION  NEW-OFFSET
first_application              first_topic                    0          24
first_application              first_topic                    1          18
first_application              first_topic                    2          17
-> Using this we can shift forward or backwards for your KAfka consumer groups
