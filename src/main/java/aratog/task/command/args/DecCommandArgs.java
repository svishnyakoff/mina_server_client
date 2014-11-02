package aratog.task.command.args;


public class DecCommandArgs extends CommandArgs {
    private int counterId;
    public DecCommandArgs(byte commandCode) {
        super(commandCode);
    }

    public DecCommandArgs(byte commandCode, int counterId) {
        super(commandCode);
        this.counterId = counterId;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    @Override
    public String toString() {
        return "DecCommandArgs{" +
                "counterId=" + counterId +
                '}';
    }
}
