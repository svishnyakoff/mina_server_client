package aratog.task.decoder;


import aratog.task.command.args.IncCommandArgs;
import org.apache.mina.core.buffer.IoBuffer;

public class IncCommandArgsDecoder extends CommandArgsDecoder<IncCommandArgs> {

    private final IncCommandArgs commandRequest;

    public IncCommandArgsDecoder(byte commandCode) {
        super(4);
        commandRequest = new IncCommandArgs(commandCode);
    }

    @Override
    protected void process(IoBuffer in) {
        int counterId = in.getInt();
        commandRequest.setCounterId(counterId);
    }

    @Override
    public IncCommandArgs getCommand() {
        return commandRequest;
    }
}
