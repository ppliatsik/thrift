package pp.model.thrift;

import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import pp.model.Message;

/**
 * Created by panos pliatsikas.
 */
public class MSerializer {

    /**
     * Serialize given object.
     *
     * @param message The object to serialize.
     * @return Array of bytes.
     */
    public static byte[] serialize(Message message) {
        TSerializer serializer = new TSerializer();
        try {
            return serializer.serialize(message);
        } catch (TException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserialize given bytes to object.
     *
     * @param bytes The bytes to deserialize.
     * @return Message.
     */
    public static Message deserialize(byte[] bytes) {
        TDeserializer deserializer = new TDeserializer();
        try {
            Message message = new Message();
            deserializer.deserialize(message, bytes);

            return message;
        } catch (TException e) {
            e.printStackTrace();
        }

        return null;
    }
}
