package pp.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import pp.model.Message;
import pp.model.thrift.MSerializer;

import java.util.Map;

/**
 * Created by panos pliatsikas.
 */
public class MessageDeserializer implements Deserializer<Message> {

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Message deserialize(String s, byte[] bytes) {
        return MSerializer.deserialize(bytes);
    }

    @Override
    public void close() {

    }
}
