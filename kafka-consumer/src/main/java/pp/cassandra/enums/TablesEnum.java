package pp.cassandra.enums;

/**
 * Created by panos pliatsikas.
 */
public enum TablesEnum {

    INFO("info");

    private String value;

    TablesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
