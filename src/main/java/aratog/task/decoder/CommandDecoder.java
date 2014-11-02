package aratog.task.decoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class CommandDecoder extends CumulativeProtocolDecoder {

    private static final String DECODER_STATE_KEY = CommandDecoder.class.getName() + ".STATE";

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        CommandArgsDecoder commandArgsDecoder = (CommandArgsDecoder)session.getAttribute(DECODER_STATE_KEY);

        if (commandArgsDecoder == null) {
            if (in.remaining() >= 1) {
                byte commandCode = in.get();
                commandArgsDecoder = builder(commandCode);
                session.setAttribute(DECODER_STATE_KEY, commandArgsDecoder);
            } else {
                return false;
            }
        }

        if (commandArgsDecoder.build(in)) {
            session.removeAttribute(DECODER_STATE_KEY);
            out.write(commandArgsDecoder.getCommand());
            return true;
        } else {
            return false;
        }
    }

    private CommandArgsDecoder builder(byte commandCode) {
        switch (commandCode) {
            case 0x01 : return new IncCommandArgsDecoder(commandCode);
            case 0x02: return new DecCommandArgsDecoder(commandCode);
        }
        throw new IllegalArgumentException("CommandBuilder does exist for the specified code");
    }





}
