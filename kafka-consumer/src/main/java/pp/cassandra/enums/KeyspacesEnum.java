package pp.cassandra.enums;

/**
 * Created by panos pliatsikas.
 */
public enum KeyspacesEnum {

    BASIC("basic");

    private String value;

    KeyspacesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
