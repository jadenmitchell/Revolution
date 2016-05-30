package org.mdev.revolution.network;

import com.google.inject.Singleton;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;

import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Singleton
public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);

    public static final boolean SOCKET_IDLE_ENABLED = false;
    public static final int SOCKET_IDLE_READ_TIME = 60;
    public static final int SOCKET_IDLE_WRITE_TIME = 60;
    public static final int SOCKET_IDLE_ALL_TIME = 180;

    private ServerBootstrap bootstrap;

    @SuppressWarnings("unused")
    private ChannelGroup channels;

    private String host;
    private List<Integer> ports;

    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final ThreadFactory wrapped = Executors.defaultThreadFactory();

        @SuppressWarnings("NullableProblems")
        public Thread newThread(final Runnable r) {
            final Thread t = wrapped.newThread(r);
            t.setDaemon(true);
            return t;
        }
    };

    @Inject
    @Deprecated
    protected Server() {
        InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);

        host = Revolution.getConfig().getString("network.host");
        ports = new ArrayList<>();
        String port = Revolution.getConfig().getString("network.port");
        if (port.contains(",")) {
            for (String p : port.split(",")) {
                ports.add(Integer.parseInt(p));
            }
        } else {
            ports.add(Integer.parseInt(port));
        }

        channels = new DefaultChannelGroup("tcp-server", GlobalEventExecutor.INSTANCE);
    }

    public void start() {
        int defaultNumWorkers = Integer.parseInt(System.getProperty("io.netty.eventLoopThreads", "0"));
        int groupThreadCount = defaultNumWorkers > 0 ? defaultNumWorkers : Runtime.getRuntime().availableProcessors() * 2; // +1?
        boolean epollEnabled = Revolution.getConfig().getString("network.epoll").equals("true");
        boolean epollAvailable = Epoll.isAvailable();

        EventLoopGroup bossGroup = new NioEventLoopGroup(groupThreadCount, threadFactory);
        EventLoopGroup workerGroup = new NioEventLoopGroup(groupThreadCount);

        if (epollEnabled) {
            if (!epollAvailable) {
                logger.error("You must be running on a Linux machine in order to use epoll!");
                logger.debug("Using the default NIO event loop group.");
            } else {
                bossGroup = new EpollEventLoopGroup(groupThreadCount, threadFactory);
                workerGroup = new EpollEventLoopGroup(groupThreadCount);
            }
        } else if (epollAvailable) {
            logger.debug("Epoll is available but is not being used.");
        }

        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(epollEnabled && epollAvailable ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1000)
                //.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_RCVBUF, 5120)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(5120))
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024, 32 * 1024))
                .childHandler(new ServerChannelInitializer(GlobalEventExecutor.INSTANCE));

        if (epollEnabled && epollAvailable) {
            bootstrap.option(EpollChannelOption.TCP_CORK, true)
                    .option(EpollChannelOption.SO_REUSEPORT, true)
                    .option(EpollChannelOption.TCP_FASTOPEN, 23);
        }

        for (int port : ports) {
            try {
                Channel ch = bootstrap.bind(new InetSocketAddress(host, port)).sync().channel();
                channels.add(ch);
                //ch.closeFuture().sync();
                logger.info("Successfully established a socket connection on " + this.host + ":" + port);
            } catch (final ChannelException e) {
                logger.error("Failed to establish a socket connection on " + this.host + ":" + port, e);
                e.printStackTrace();
                System.exit(1);
            } catch (InterruptedException e) {
                logger.error("Interrupted while establishing a socket connection on " + this.host + ":" + port, e);
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void stop() {
        channels.flush();
        channels.close();
        bootstrap.config().group().shutdownGracefully();
        bootstrap.config().childGroup().shutdownGracefully();
    }

    public ChannelGroup getChannels() {
        return channels;
    }
}
