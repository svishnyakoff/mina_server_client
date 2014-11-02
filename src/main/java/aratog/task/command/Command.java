package aratog.task.command;

import aratog.task.request.CommandArgs;


public interface  Command {

    public void execute(CommandArgs commandArgs);
}
