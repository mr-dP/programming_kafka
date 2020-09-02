package lesson1kafkaproducer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class DemoProducer2 {
    public static void main(String[] args) {

//        create a Logger for the class
        final Logger logger = LoggerFactory.getLogger(DemoProducer2.class);

        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);


        int i = 0;
        while(i < 10)
        {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "i say hello " + Integer.toString(i));

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
            i++;
        }

        producer.flush();
        producer.close();

    }

}
