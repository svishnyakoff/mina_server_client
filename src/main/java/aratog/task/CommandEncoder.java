package aratog.task;

import aratog.task.request.CommandRequest;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Created by svishnyakov on 01.11.14.
 */
public class CommandEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {

        CommandRequest commandRequest = (CommandRequest) message;
        IoBuffer buffer = IoBuffer.allocate(5, false);
        buffer.put(commandRequest.getCommandCode());
        buffer.putInt(commandRequest.getCounterId());
        buffer.flip();
        out.write(buffer);
    }
}
