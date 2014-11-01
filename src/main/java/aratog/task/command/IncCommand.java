package aratog.task.command;

import aratog.task.request.CommandRequest;
import aratog.task.request.IncCommandRequest;


public class IncCommand implements Command {
    @Override
    public void execute(CommandRequest commandRequest) {
        IncCommandRequest args = (IncCommandRequest) commandRequest;
        System.out.println("increment " + args.getCounterId() );
    }
}
