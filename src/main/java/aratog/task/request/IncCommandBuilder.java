package aratog.task.request;


import org.apache.mina.core.buffer.IoBuffer;

public class IncCommandBuilder extends CommandBuilder<IncCommandArgs> {

    private final IncCommandArgs commandRequest;

    public IncCommandBuilder(byte commandCode) {
        super(new int[]{4});
        commandRequest = new IncCommandArgs(commandCode);
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

    @Override
    public IncCommandArgs getCommand() {
        return commandRequest;
    }
}
