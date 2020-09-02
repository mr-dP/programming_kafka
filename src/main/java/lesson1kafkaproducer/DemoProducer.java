package lesson1kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class DemoProducer {
    public static void main(String[] args) {

//        1.    Create Producer properties
        Properties props = new Properties();
//        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.setProperty("key.serializer", StringSerializer.class.getName());
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        props.setProperty("value.serializer", StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

//        Key Serializer and Value Serializer helps the Producer know what type of value we are sending to Kafka and how these would be Serialized to Bytes (0s and 1s)
//        The Kafka client will convert whatever we send to Kafka into Bytes


//        2.    Create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
//        we want the key and value to be Strings


        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "i say hello");

//        3.    Send Data
        producer.send(record);
//        the "send()" function takes a "ProducerRecord" as an input
//        this is Asynchronous
//        to wait for the data to be produced:-
        producer.flush();
        producer.close();

    }

}
