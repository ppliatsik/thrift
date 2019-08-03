package pp;

import com.datastax.driver.core.utils.UUIDs;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pp.cassandra.CassandraConnector;
import pp.cassandra.TablesCreator;
import pp.cassandra.enums.KeyspacesEnum;
import pp.cassandra.repositories.InfoRepository;
import pp.kafka.KafkaConsumerP;
import pp.model.InfoModel;
import pp.model.Message;

import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * Created by panos pliatsikas.
 */
public class ConsumerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerMain.class);

    public static void main(String[] args) {
        CassandraConnector cassandraConnector = new CassandraConnector();
        cassandraConnector.connect("127.0.0.1", 9042, KeyspacesEnum.BASIC.getValue());
        TablesCreator creator = new TablesCreator(cassandraConnector.getSession());
        creator.postConstruct();
        InfoRepository infoRepository = new InfoRepository(cassandraConnector.getMappingManager());

        KafkaConsumerP kafkaConsumer = new KafkaConsumerP();
        Consumer<Long, Message> consumer = kafkaConsumer.create();

        boolean stop = false;
        while (!stop) {
            ConsumerRecords<Long, Message> records = consumer.poll(Duration.ofMillis(10));
            if (records.count() == 0) continue;

            for (ConsumerRecord<Long, Message> record : records) {
                Message message = record.value();

                LOGGER.info("Receive from kafka: {}", message.toString());
                LOGGER.info("Metadata: {}, {}, {}", record.key(), record.partition(), record.offset());

                if ("end".equals(message.getM())) {
                    stop = true;
                    break;
                }

                InfoModel infoModel = new InfoModel();
                infoModel.setId(UUIDs.timeBased());
                infoModel.setType(message.getV());
                infoModel.setMessage(message.getM());
                infoModel.setDate(new Date(message.getTime()));
                infoRepository.save(infoModel);
            }
        }

        LOGGER.info("Start print the results:");
        List<InfoModel> infoModels = infoRepository.getAll();
        infoModels.forEach(
                info -> LOGGER.info("Cassandra record: {}", info.toString())
        );

        kafkaConsumer.close();
        cassandraConnector.close();
    }
}
