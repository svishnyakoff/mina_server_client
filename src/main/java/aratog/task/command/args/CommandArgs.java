package aratog.task.command.args;


public class CommandArgs {

    private byte commandCode;

    public CommandArgs(byte commandCode) {
        this.commandCode = commandCode;
    }

    public byte getCommandCode() {
        return commandCode;
    }
}
