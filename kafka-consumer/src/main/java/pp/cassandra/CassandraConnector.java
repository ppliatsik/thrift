package pp.cassandra;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

/**
 * Created by panos pliatsikas.
 */
public class CassandraConnector {

    private Cluster cluster;

    private Session session;

    private MappingManager mappingManager;

    public void connect(String node, int port, String keyspace) {
        cluster = Cluster.builder()
                .addContactPoint(node)
                .withPort(port)
                .withoutMetrics()
                .build();
        session = cluster.connect();
        createKeyspace(keyspace, "SimpleStrategy", 1);
        session.execute("USE " + keyspace);
        mappingManager = new MappingManager(session);
    }

    public void close() {
        session.close();
        cluster.close();
    }

    public Session getSession() {
        return session;
    }

    public MappingManager getMappingManager() {
        return mappingManager;
    }

    /**
     * Creates keyspace.
     *
     * @param keyspaceName        Keyspace name.
     * @param replicationStrategy Replication strategy.
     * @param replicationFactor   Replication factor.
     */
    private void createKeyspace(String keyspaceName,
                                String replicationStrategy,
                                int replicationFactor) {
        String query = "CREATE KEYSPACE IF NOT EXISTS " +
                keyspaceName +
                " WITH replication = {" +
                "'class':'" +
                replicationStrategy +
                "','replication_factor':" +
                replicationFactor +
                "};";
        session.execute(query);
    }
}
