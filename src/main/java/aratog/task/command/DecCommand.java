package aratog.task.command;


import aratog.task.request.CommandRequest;
import aratog.task.request.DecCommandRequest;
import aratog.task.request.IncCommandRequest;

public class DecCommand implements Command{
    @Override
    public void execute(CommandRequest commandRequest) {
        DecCommandRequest args = (DecCommandRequest) commandRequest;
        System.out.println("decrement " + args.getCounterId() );
    }
}
