package aratog.task;


import aratog.task.request.CommandRequest;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.util.Random;

public class ClientMain {

    public static void main(String[] args) throws InterruptedException {

        SocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CommandEncoder(), new CommandDecoder()));
        connector.setHandler(new ServerHandler());
        ConnectFuture connect = connector.connect(new InetSocketAddress(9001));
        connect.awaitUninterruptibly();
        IoSession session = connect.getSession();
        Random random = new Random();
        while (true) {
            session.write(new CommandRequest((byte)0x01, random.nextInt(100)));
            session.write(new CommandRequest((byte)0x02, random.nextInt(100)));
            Thread.sleep(1000);
        }

    }
}
