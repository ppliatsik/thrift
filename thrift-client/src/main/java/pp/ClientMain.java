package pp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pp.model.Message;
import pp.socket.ClientSock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by panos pliatsikas.
 */
public class ClientMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) {
        ClientSock clientSock = new ClientSock("127.0.0.1", 19861);
        clientSock.connect();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            String root = System.getProperty("user.dir");
            Path path = Paths.get(
                    root + "/thrift-client/src/main/resources"
            );
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            AtomicBoolean stop = new AtomicBoolean(false);
            while (!stop.get()) {
                WatchKey watchKey = watchService.poll(500, TimeUnit.MILLISECONDS);
                if (watchKey != null) {
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        String name = path.toString() + "/" + event.context().toString();
                        LOGGER.info("Try to read file: {}", name);
                        List<Message> messages = readFile(name);

                        messages.forEach(
                                m -> {
                                    clientSock.send(m);
                                    LOGGER.info("Send to Socket: {}", m.toString());
                                    stop.set("end".equals(m.getM()));
                                }
                        );
                    }

                    watchKey.reset();
                }
            }

            watchService.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        clientSock.disconnect();
    }

    /**
     * Read contents of given file and creates a list of
     * messages.
     *
     * @param name The name of file.
     * @return List of Message.
     */
    private static List<Message> readFile(String name) {
        LinkedList<Message> messages = new LinkedList<>();

        File file = new File(name);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                Message message = new Message((short) 1, new Date().getTime(), line);
                messages.add(message);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
