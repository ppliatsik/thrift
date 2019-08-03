package pp;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pp.kafka.KafkaProducerP;
import pp.model.Message;
import pp.socket.ServerSock;

import java.util.concurrent.ExecutionException;

/**
 * Created by panos pliatsikas.
 */
public class ServerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        ServerSock serverSock = new ServerSock(19861);
        serverSock.connect();

        KafkaProducerP kafkaProducer = new KafkaProducerP();
        Producer<Long, Message> producer = kafkaProducer.create();

        while (true) {
            Message message = serverSock.receive();
            LOGGER.info("Receive from Socket: {}", message.getM());

            long index = System.currentTimeMillis();
            ProducerRecord<Long, Message> record = new ProducerRecord<>(
                    KafkaProducerP.TOPIC, index, message
            );

            try {
                RecordMetadata metadata = producer.send(record).get();
                LOGGER.info("Send to kafka: {}", message.toString());
                LOGGER.info("Metadata: {}, {}, {}", record.key(), metadata.partition(), metadata.offset());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            if ("end".equals(message.getM())) break;
        }

        kafkaProducer.close();
        serverSock.disconnect();
    }
}
