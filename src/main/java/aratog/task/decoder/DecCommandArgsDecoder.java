package aratog.task.decoder;


import aratog.task.command.args.DecCommandArgs;
import org.apache.mina.core.buffer.IoBuffer;

public class DecCommandArgsDecoder extends CommandArgsDecoder<DecCommandArgs> {

    private final DecCommandArgs commandRequest;

    public DecCommandArgsDecoder(byte commandCode) {
        super(4);
        commandRequest = new DecCommandArgs(commandCode);
    }

    @Override
    protected void decode(IoBuffer in) {
        int counterId = in.getInt();
        commandRequest.setCounterId(counterId);
    }

    public DecCommandArgs getCommand() {
        return commandRequest;
    }
}
