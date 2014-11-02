package aratog.task;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class CommandEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
//        CommandArgs commandArgs = (CommandArgs) message;
//        IoBuffer buffer = IoBuffer.allocate(5, false);
//        buffer.put(commandArgs.getCommandCode());
//        buffer.putInt(commandArgs.getCounterId());
//        buffer.flip();
//        out.write(buffer);
    }
}
