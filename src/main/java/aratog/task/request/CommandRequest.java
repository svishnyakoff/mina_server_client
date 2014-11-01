package aratog.task.request;


public class CommandRequest {

    private byte commandCode;
    private int counterId;

    public CommandRequest(byte commandCode) {
        this.commandCode = commandCode;
    }

    public CommandRequest(byte commandCode, int counterId) {
        this.commandCode = commandCode;
        this.counterId = counterId;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public byte getCommandCode() {
        return commandCode;
    }
}
