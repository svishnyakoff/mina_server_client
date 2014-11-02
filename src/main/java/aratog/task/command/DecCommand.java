package aratog.task.command;


import aratog.task.CounterHistory;
import aratog.task.request.CommandArgs;
import aratog.task.request.DecCommandArgs;
import aratog.task.spring.CounterHistoryService;
import aratog.task.spring.CounterService;
import aratog.task.spring.SpringUtils;

public class DecCommand implements Command {
    private CounterService counterService = SpringUtils.getInstance().get(CounterService.class);
    private CounterHistoryService counterHistoryService = SpringUtils.getInstance().get(CounterHistoryService.class);

    public void execute(CommandArgs commandArgs) {
        DecCommandArgs decCommandArgs = (DecCommandArgs) commandArgs;
        if (counterService.decrementCounter(decCommandArgs.getCounterId())) {
            counterHistoryService.insert(new CounterHistory(decCommandArgs.getCounterId(), "dec"));
        }
    }
}
