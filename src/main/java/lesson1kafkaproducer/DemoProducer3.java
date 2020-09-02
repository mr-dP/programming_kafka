package lesson1kafkaproducer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class DemoProducer3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        create a Logger for the class
        final Logger logger = LoggerFactory.getLogger(DemoProducer3.class);

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

        int i = 0;
        while (i < 10) {
            String topic = "first_topic";
            String value = "mrdp " + i;
            String key = "key_" + i;

            logger.info("KEY => " + key);

            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception ex) {
//                the function "onCompletion()" executes every time a record is successfully sent or there is an Exception
                    if (ex == null) {
//                    the record was successfully sent
                        logger.info(
                                "Metadata received:- " + "\n" +
                                        "Topic = " + metadata.topic() + "\n" +
                                        "Partition = " + metadata.partition() + "\n" +
                                        "Offset = " + metadata.offset() + "\n" +
                                        "Timestamp = " + metadata.timestamp()
                        );
                    } else {
                        logger.error("Error = " + ex);
                    }

                }
            });
//                    .get();         //      to make it synchronous
            i++;
        }

        producer.flush();
        producer.close();

    }

}
