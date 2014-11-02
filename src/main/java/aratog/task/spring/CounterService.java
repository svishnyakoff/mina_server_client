package aratog.task.spring;

import aratog.task.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CounterService {

    @Autowired
    private MongoOperations mongoOperations;

    public boolean incrementCounter(int id) {
        Query query = query(where("_id").is(id).and("value").lt(100));
        Update update = new Update().inc("value", 1);
        return mongoOperations.findAndModify(query, update, Counter.class) != null;
    }

    public boolean decrementCounter(int id) {
        Query query = query(where("_id").is(id).and("value").gt(0));
        Update update = new Update().inc("value", -1);
        return mongoOperations.findAndModify(query, update, Counter.class) != null;
    }

    public boolean existsWithId(int id) {
        return mongoOperations.findById(id, Counter.class) != null;
    }

    public void save(Counter counter) {
        mongoOperations.save(counter);
    }

}
