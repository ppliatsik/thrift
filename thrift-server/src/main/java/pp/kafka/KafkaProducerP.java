package pp.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import pp.model.Message;

import java.util.Properties;

/**
 * Created by panos pliatsikas.
 */
public class KafkaProducerP {

    public final static String TOPIC = "panos";

    private final static String BOOTSTRAP_SERVER = "localhost:9092,localhost:9093,localhost:9094";

    private Producer<Long, Message> producer;

    /**
     * Creates a kafka producer.
     *
     * @return Producer.
     */
    public Producer<Long, Message> create() {
        if (producer == null) {
            Properties properties = new Properties();
            properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
            properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaProducerP");
            properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());

            producer = new KafkaProducer<>(properties);
        }

        return producer;
    }

    /**
     * Close producer.
     */
    public void close() {
        producer.flush();
        producer.close();
    }
}
