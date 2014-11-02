package aratog.task.request;


import org.apache.mina.core.buffer.IoBuffer;

public class DecCommandBuilder extends CommandBuilder<DecCommandArgs> {

    private final DecCommandArgs commandRequest;

    public DecCommandBuilder(byte commandCode) {
        super(new int[]{4});
        commandRequest = new DecCommandArgs(commandCode);
    }

    @Override
    protected void process(IoBuffer in, int step) {
        switch (step) {
            case 0: {
                int counterId = in.getInt();
                commandRequest.setCounterId(counterId);
                break;
            }
        }
    }

    public DecCommandArgs getCommand() {
        return commandRequest;
    }
}
