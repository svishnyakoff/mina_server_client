package aratog.task;


import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerMain {
    private static final int PORT = 9001;

    public static void main(String[] args) throws IOException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast("decoder", new ProtocolCodecFilter(new CommandEncoder(), new CommandDecoder()));
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("server is listenig at port " + PORT);
    }
}
