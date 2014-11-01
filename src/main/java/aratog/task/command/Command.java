package aratog.task.command;

import aratog.task.request.CommandRequest;

/**
 * Created by svishnyakov on 01.11.14.
 */
public interface  Command {

    public void execute(CommandRequest commandRequest);
}
