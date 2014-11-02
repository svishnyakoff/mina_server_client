package aratog.task.spring;

import aratog.task.CounterHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

@Service
public class CounterHistoryService {

    @Autowired
    private MongoOperations mongoOperations;

    public void insert(CounterHistory history) {
        mongoOperations.insert(history);
    }
}
