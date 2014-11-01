package aratog.task;

import aratog.task.request.*;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class CommandDecoder extends CumulativeProtocolDecoder {

    private static final String DECODER_STATE_KEY = CommandDecoder.class.getName() + ".STATE";

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        CommandBuilder commandBuilder = (CommandBuilder)session.getAttribute(DECODER_STATE_KEY);

        if (commandBuilder == null) {
            if (in.remaining() >= 1) {
                byte commandCode = in.get();
                commandBuilder = builder(commandCode);
                session.setAttribute(DECODER_STATE_KEY, commandBuilder);
            } else {
                return false;
            }
        }

        commandBuilder.build(in);
        if (commandBuilder.buildCompleted()) {
            session.removeAttribute(DECODER_STATE_KEY);
            out.write(commandBuilder.getCommand());
            return true;
        } else {
            return false;
        }
    }

    private CommandBuilder builder(byte commandCode) {
        switch (commandCode) {
            case 0x01 : return new IncCommandBuilder(commandCode);
            case 0x02: return new DecCommandBuilder(commandCode);
        }
        throw new IllegalArgumentException("CommandBuilder does exist for the specified code");
    }





}
