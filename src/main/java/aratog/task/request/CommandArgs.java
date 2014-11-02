package aratog.task.request;


public class CommandArgs {

    private byte commandCode;

    public CommandArgs(byte commandCode) {
        this.commandCode = commandCode;
    }

    public byte getCommandCode() {
        return commandCode;
    }
}
