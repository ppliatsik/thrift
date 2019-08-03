package pp.cassandra;

import com.datastax.driver.core.Session;
import pp.cassandra.enums.TablesEnum;

/**
 * Created by panos pliatsikas.
 */
public class TablesCreator {

    private Session session;

    public TablesCreator(Session session) {
        this.session = session;
    }

    /**
     * Creates keyspace and tables.
     */
    public void postConstruct() {
        createInfoTable();
    }

    /**
     * Creates BOOKS table.
     */
    private void createInfoTable() {
        String query = "CREATE TABLE IF NOT EXISTS " +
                TablesEnum.INFO.getValue() +
                "(id uuid PRIMARY KEY, " +
                "type int, " +
                "message text, " +
                "date timestamp);";
        session.execute(query);
    }
}
