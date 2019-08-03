package pp.socket;

import pp.model.Message;
import pp.model.thrift.MSerializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by panos pliatsikas.
 */
public class ServerSock {

    private Socket socket;

    private ServerSocket serverSocket;

    private int port;

    private DataInputStream dataInputStream;

    public ServerSock(int port) {
        this.port = port;
    }

    /**
     * Creates socket.
     */
    public void connect() {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Receive message from socket.
     *
     * @return Message.
     */
    public Message receive() {
        try {
            int length = dataInputStream.readInt();
            if (length > 0) {
                byte[] bytes = new byte[length];
                dataInputStream.readFully(bytes, 0, bytes.length);
                return MSerializer.deserialize(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Close socket.
     */
    public void disconnect() {
        try {
            dataInputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
