package aratog.task.decoder;


import org.apache.mina.core.buffer.IoBuffer;

abstract public class CommandArgsDecoder<T> {
    private final int requiredBytes;


    public CommandArgsDecoder(int requiredBytes) {
        this.requiredBytes = requiredBytes;
    }

    public boolean build(IoBuffer in) {
        if (in.remaining() >= requiredBytes()) {
            decode(in);
            return true;
        }
        return false;
    }

    abstract protected void decode(IoBuffer in);


    protected int requiredBytes() {
        return requiredBytes;
    }

    public T getCommand() {
        throw new UnsupportedOperationException();
    }
}
