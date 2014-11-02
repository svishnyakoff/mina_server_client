package aratog.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Counter.COLLECTION_NAME)
public class Counter {
    public static final String COLLECTION_NAME = "counter";

    @Id
    private int id;
    private int value;

    public Counter() {
    }

    public Counter(int id, int value) {
        this.value = value;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
