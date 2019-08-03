package pp.socket;

import pp.model.Message;
import pp.model.thrift.MSerializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by panos pliatsikas.
 */
public class ClientSock {

    private Socket socket;

    private String host;

    private int port;

    private DataOutputStream dataOutputStream;

    public ClientSock(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Connect to socket.
     */
    public void connect() {
        try {
            socket = new Socket(host, port);

            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send message to socket.
     *
     * @param message The message to send.
     */
    public void send(Message message) {
        try {
            byte[] bytes = MSerializer.serialize(message);
            if (bytes != null) {
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnect from socket.
     */
    public void disconnect() {
        try {
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
