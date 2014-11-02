package aratog.task;


import aratog.task.spring.CounterService;
import aratog.task.spring.SpringUtils;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class ServerMain {
    private static final int PORT = 9001;
    private static final int MAX_HANDLER_THREADS = 50;

    public static void main(String[] args) throws IOException {
        initDB();

        IoAcceptor acceptor = new NioSocketAcceptor();
        Executor handlerExecutor = new ThreadPoolExecutor(0, MAX_HANDLER_THREADS, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast("decoder", new ProtocolCodecFilter(new CommandEncoder(), new CommandDecoder()));
        acceptor.getFilterChain().addLast("thread-pool", new ExecutorFilter(handlerExecutor));
        acceptor.setHandler(new ServerHandler());
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("server is listening at port " + PORT);
    }

    private static void initDB() {
        CounterService counterService = SpringUtils.getInstance().get(CounterService.class);
        for(int i = 1; i <= 100; i++) {
            if (!counterService.existsWithId(i)) {
                counterService.save(new Counter(i, 0));
            }
        }
    }
}
