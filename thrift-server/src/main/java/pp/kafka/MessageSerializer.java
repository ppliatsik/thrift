package pp.kafka;

import org.apache.kafka.common.serialization.Serializer;
import pp.model.Message;
import pp.model.thrift.MSerializer;

import java.util.Map;

/**
 * Created by panos pliatsikas.
 */
public class MessageSerializer implements Serializer<Message> {

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Message message) {
        return MSerializer.serialize(message);
    }

    @Override
    public void close() {

    }
}
