package aratog.task.request;


import org.apache.mina.core.buffer.IoBuffer;

abstract public class CommandBuilder<T> {
    protected int step = 0;
    private final int[] requiredBytes;


    public CommandBuilder(int[] requiredBytes) {
        this.requiredBytes = requiredBytes;
    }

    public void build(IoBuffer in) {
        while (!buildCompleted() && in.remaining() >= requiredBytes()) {
            process(in, step++);
        }
    }

    abstract protected void process(IoBuffer in, int step);

    public boolean  buildCompleted() {
        return step >= requiredBytes.length;
    }

    protected int requiredBytes() {
        return requiredBytes[step];
    }

    public T getCommand() {
        throw new UnsupportedOperationException();
    }
}
