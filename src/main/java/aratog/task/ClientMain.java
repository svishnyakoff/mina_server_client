package aratog.task;


import aratog.task.command.args.DecCommandArgs;
import aratog.task.command.args.IncCommandArgs;
import aratog.task.decoder.CommandDecoder;
import aratog.task.encoder.CommandEncoder;
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
    private volatile static boolean hasConnections = true;

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
                if (sessionSet.isEmpty()) {
                    hasConnections = false;
                }
            }
        });
        connect(connector, inetSocketAddresses);
        sendMessages(connector);

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

    private static void sendMessages(SocketConnector connector) throws InterruptedException {
        Random random = new Random();
        final int maxCounterId = 100;
        while (hasConnections) {
            for (IoSession session : sessionSet) {
                boolean inc = random.nextBoolean();
                if (inc) {
                    session.write(new IncCommandArgs((byte) 0x01, random.nextInt(maxCounterId)));
                } else {
                    session.write(new DecCommandArgs((byte) 0x02, random.nextInt(maxCounterId)));
                }

            }
            Thread.sleep(10);
        }

        LOG.info("There is no active server connections. Closing client");
        connector.dispose();

    }

    private static InetSocketAddress[] parseServerAddresses(String[] args) {
        if (args.length == 0) {
            LOG.error("Please provide at least 1 server you would like to connect to");
            throw new IllegalStateException();
        }

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
