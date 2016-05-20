package org.mdev.revolution.network;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import org.mdev.revolution.network.codec.PacketDecoder;
import org.mdev.revolution.network.codec.PacketEncoder;
import org.mdev.revolution.network.codec.XMLPolicyDecoder;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final EventExecutorGroup eventExecutor;

    public ServerChannelInitializer(EventExecutorGroup eventExecutor) {
        this.eventExecutor = eventExecutor;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.config().setTrafficClass(0x18);

        ch.pipeline().addLast("loggingHandler", new LoggingHandler(LogLevel.DEBUG))
                .addLast("policyDecoder", new XMLPolicyDecoder())
                .addLast("stringEncoder", new StringEncoder(CharsetUtil.UTF_8))
                .addLast("packetEncoder", new PacketEncoder())
                .addLast("packetDecoder", new PacketDecoder())
                .addLast(eventExecutor, "channelHandler", ServerChannelHandler.INSTANCE);

        if (Server.SOCKET_IDLE_ENABLED) {
            ch.pipeline().addLast("idleHandler",
                    new IdleStateHandler(
                            Server.SOCKET_IDLE_READ_TIME,
                            Server.SOCKET_IDLE_WRITE_TIME,
                            Server.SOCKET_IDLE_ALL_TIME,
                            TimeUnit.SECONDS));
        }
    }
}
