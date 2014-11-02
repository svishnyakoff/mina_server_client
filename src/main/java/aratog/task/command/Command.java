package aratog.task.command;

import aratog.task.command.args.CommandArgs;


public interface  Command {

    public void execute(CommandArgs commandArgs);
}
