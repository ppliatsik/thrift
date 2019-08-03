package pp.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by panos pliatsikas.
 */
@Table(name = "info")
public class InfoModel implements Serializable {

    private static final long serialVersionUID = -6333589554941245491L;

    @PartitionKey
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private int type;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private Date date;

    public InfoModel() {
    }

    public InfoModel(UUID id, int type, String message, Date date) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoModel)) return false;
        InfoModel infoModel = (InfoModel) o;
        return getId().equals(infoModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "InfoModel{" +
                "id=" + id +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
