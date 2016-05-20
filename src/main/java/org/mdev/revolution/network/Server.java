package org.mdev.revolution.network;

import com.google.inject.Singleton;
import io.netty.bootstrap.ServerBootstrap;
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

        /*if (ports.size() > MAX_CONNECTION_PORTS) {
            logger.error("Server ports have exceeded it's maximum capacity:\n   MAX_CONNECTION_PORTS: {}\n   CONNECTION_PORTS: {}\n   ports[]: {}",
                    MAX_CONNECTION_PORTS,
                    ports.size(),
                    ports);
            while (ports.size() <= MAX_CONNECTION_PORTS) {
                int p = ports.get(ports.size() + 1);
                ports.remove(p);
            }
            logger.debug("Modified the connection ports to the allowed amount" +
                    "\n   ports[]: " + String.valueOf(ports));
        }*/

        channels = new DefaultChannelGroup("tcp-server", GlobalEventExecutor.INSTANCE);
    }

    public void start() {
        if (channels == null) {
            throw new IllegalStateException("Possible use of deprecated constructor to initialize a new instance of the Server.");
        }

        int defaultNumWorkers = Integer.parseInt(System.getProperty("io.netty.eventLoopThreads", "0"));
        int groupThreadCount = defaultNumWorkers > 0 ? defaultNumWorkers : Runtime.getRuntime().availableProcessors() * 2; // +1?
        //int ioGroupThreadCount =  acceptGroupThreadCount;
        boolean epollEnabled = Revolution.getConfig().getString("network.epoll").equals("true");
        boolean epollAvailable = Epoll.isAvailable();

        EventLoopGroup bossGroup = new NioEventLoopGroup(groupThreadCount);
        EventLoopGroup workerGroup = new NioEventLoopGroup(groupThreadCount);

        if (epollEnabled) {
            if (!epollAvailable) {
                logger.error("You must be running on a Linux machine in order to use epoll!");
                logger.debug("Using the default NIO event loop group.");
            } else {
                bossGroup = new EpollEventLoopGroup(groupThreadCount);
                workerGroup = new EpollEventLoopGroup(groupThreadCount);
            }
        } else if (epollAvailable) {
            logger.debug("Epoll is available but is not being used.");
        }

        bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(epollEnabled && epollAvailable ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 500)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
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
        bootstrap.group().shutdownGracefully();
        bootstrap.childGroup().shutdownGracefully();
    }

    public ChannelGroup getChannels() {
        return channels;
    }
}
