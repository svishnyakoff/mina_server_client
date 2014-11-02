package aratog.task;


import aratog.task.request.DecCommandArgs;
import aratog.task.request.IncCommandArgs;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

public class ClientMain {
    private static final Logger LOG = LoggerFactory.getLogger(ClientMain.class);
    private static CopyOnWriteArraySet<IoSession> sessionSet = new CopyOnWriteArraySet<IoSession>();

    public static void main(String[] args) throws InterruptedException {

        InetSocketAddress[] inetSocketAddresses = parseServerAddresses(args);
        LOG.info("Connecting to {}", Arrays.toString(inetSocketAddresses));
        SocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CommandEncoder(), new CommandDecoder()));
        connector.setHandler(new IoHandlerAdapter(){
            @Override
            public void sessionClosed(IoSession session) throws Exception {
                sessionSet.remove(session);
            }
        });
        connect(connector, inetSocketAddresses);
        sendMessages();

    }

    private static void connect(SocketConnector connector, InetSocketAddress[] inetSocketAddresses) {
        for (InetSocketAddress inetSocketAddress : inetSocketAddresses) {
            ConnectFuture connect = connector.connect(inetSocketAddress);
            connect.addListener(new IoFutureListener<IoFuture>() {
                @Override
                public void operationComplete(IoFuture future) {
                    sessionSet.add(future.getSession());
                }
            });
        }
    }

    private static void sendMessages() throws InterruptedException {
        Random random = new Random();
        final int maxCounterId = 100;
        while (true) {
            for (IoSession session : sessionSet) {
                boolean inc = random.nextBoolean();
                if (inc) {
                    session.write(new IncCommandArgs((byte) 0x01, random.nextInt(maxCounterId)));
                } else {
                    session.write(new DecCommandArgs((byte) 0x02, random.nextInt(maxCounterId)));
                }

            }
            Thread.sleep(100);
        }
    }

    private static InetSocketAddress[] parseServerAddresses(String[] args) {
        InetSocketAddress[] result = new InetSocketAddress[args.length];
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.contains(":")) {
                String[] hostPort = arg.split(":");
                result[i] = new InetSocketAddress(hostPort[0], Integer.parseInt(hostPort[1]));
            } else {
                result[i] = new InetSocketAddress(Integer.parseInt(arg));
            }
        }

        return result;
    }
}
