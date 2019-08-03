package pp.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import pp.model.Message;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by panos pliatsikas.
 */
public class KafkaConsumerP {

    private final static String TOPIC = "panos";

    private final static String BOOTSTRAP_SERVER = "localhost:9092,localhost:9093,localhost:9094";

    private Consumer<Long, Message> consumer;

    /**
     * Creates kafka consumer.
     *
     * @return Consumer.
     */
    public Consumer<Long, Message> create() {
        if (consumer == null) {
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
            properties.put(ConsumerConfig.GROUP_ID_CONFIG, "KafkaConsumerP");
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());

            consumer = new KafkaConsumer<>(properties);
            consumer.subscribe(Collections.singletonList(TOPIC));
        }

        return consumer;
    }

    /**
     * Close consumer.
     */
    public void close() {
        consumer.close();
    }
}
