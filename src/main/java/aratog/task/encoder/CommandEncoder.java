package aratog.task.encoder;

import aratog.task.command.args.CommandArgs;
import aratog.task.command.args.DecCommandArgs;
import aratog.task.command.args.IncCommandArgs;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class CommandEncoder extends ProtocolEncoderAdapter {

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CommandArgs commandArgs = (CommandArgs) message;
        IoBuffer buffer = IoBuffer.allocate(5, false);
        if (0x01 == commandArgs.getCommandCode()) {
            encodeIncCommandArgs(buffer, (IncCommandArgs) commandArgs);
        } else if (0x02 == commandArgs.getCommandCode()) {
            encodeDecCommandArgs(buffer, (DecCommandArgs) commandArgs);
        }
        buffer.flip();
        out.write(buffer);
    }

    private IoBuffer encodeIncCommandArgs(IoBuffer buffer, IncCommandArgs commandArgs) {
        buffer.put(commandArgs.getCommandCode());
        buffer.putInt(commandArgs.getCounterId());
        return buffer;
    }

    private IoBuffer encodeDecCommandArgs(IoBuffer buffer, DecCommandArgs commandArgs) {
        buffer.put(commandArgs.getCommandCode());
        buffer.putInt(commandArgs.getCounterId());
        return buffer;
    }
}
