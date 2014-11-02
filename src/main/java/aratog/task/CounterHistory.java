package aratog.task;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = CounterHistory.COLLECTION_NAME)
public class CounterHistory {
    public static final String COLLECTION_NAME = "history";

    @Id
    private String id;
    @Indexed
    private int counterId;
    private String commandName;

    public CounterHistory() {
    }

    public CounterHistory(int counterId, String commandName) {
        this.counterId = counterId;
        this.commandName = commandName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }
}
