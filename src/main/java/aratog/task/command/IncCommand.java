package aratog.task.command;

import aratog.task.CounterHistory;
import aratog.task.request.CommandArgs;
import aratog.task.request.IncCommandArgs;
import aratog.task.spring.CounterHistoryService;
import aratog.task.spring.CounterService;
import aratog.task.spring.SpringUtils;


public class IncCommand implements Command {
    private CounterService counterService = SpringUtils.getInstance().get(CounterService.class);
    private CounterHistoryService counterHistoryService = SpringUtils.getInstance().get(CounterHistoryService.class);

    @Override
    public void execute(CommandArgs commandArgs) {
        IncCommandArgs incCommandArgs = (IncCommandArgs) commandArgs;
        if (counterService.incrementCounter(incCommandArgs.getCounterId())) {
            counterHistoryService.insert(new CounterHistory(incCommandArgs.getCounterId(), "inc"));
        }
    }

}
