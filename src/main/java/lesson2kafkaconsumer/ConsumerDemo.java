package lesson2kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getName());

//        1.    Create Consumer properties
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        When the Producer takes a String, it serializes it into Bytes and sends it to KAFKA
//        When KAFKA send these Bytes back to the Consumer, our Consumer has to take the Bytes and create a STRING from it
//        This process is called Deserialization
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-app-group");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        earliest  =>  read from very beginning of the TOPIC
//        latest    =>  read only from the new messages onwards
//        none      =>  throw exception to the consumer if no previous offset is found for the consumer's group


//        2.    create a Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);


//        3.    subscribe to out topics
        consumer.subscribe(Collections.singleton("first_topic"));
//        by doing "Collections.singleton()" we only subscribe to only one topic
//        we can subscribe to many topics by using "Arrays.asList(topic1, topic2, ...)"


//        4.    get data
        while (true) {
            ConsumerRecords<String, String> cRecords = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : cRecords) {
                logger.info("Key => " + record.key());
                logger.info("Value => " + record.value());
                logger.info("Partition => " + record.partition());
                logger.info("Offset => " + record.offset());
            }
        }

    }
}
