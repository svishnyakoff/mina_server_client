package aratog.task.request;


import org.apache.mina.core.buffer.IoBuffer;

public class DecCommandBuilder extends CommandBuilder<DecCommandRequest> {

    private final DecCommandRequest commandRequest;

    public DecCommandBuilder(byte commandCode) {
        super(new int[]{4});
        commandRequest = new DecCommandRequest(commandCode);
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

    public DecCommandRequest getCommand() {
        return commandRequest;
    }
}
