package org.mdev.revolution.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;

import java.net.InetSocketAddress;
import java.util.List;

public class XMLPolicyDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger logger = LogManager.getLogger(XMLPolicyDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        // less than 5 bytes == garbage data
        if (buffer.readableBytes() < 5) {
            return;
        }

        char delimiter = (char)buffer.readByte();

        buffer.resetReaderIndex();
        InetSocketAddress sock = (InetSocketAddress)ctx.channel().remoteAddress();

        System.out.println(sock.getHostName());
        if (delimiter == ':') {
            if (!sock.getHostName().equals("127.0.0.1")) {
                logger.warn("An attempted RCON connection rejected from IP Address: {0}", sock.getHostName());
                ctx.close();
                return;
            }

            logger.debug("An RCON client successfully has connected from IP Address: {0}.", sock.getHostName());
            ctx.pipeline().addFirst("rconDecoder", Revolution.getInjector().getInstance(RconDecoder.class));
            ctx.pipeline().remove(PacketDecoder.class);
            ctx.pipeline().remove(this);
        }
        if (delimiter == '<') {
            String policy = "<?xml version=\"1.0\"?>\r\n"
                    + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
                    + "<cross-domain-policy>\r\n"
                    + "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n"
                    + "</cross-domain-policy>\0";

            ctx.writeAndFlush(Unpooled.copiedBuffer(policy.getBytes()).retain()).addListener(ChannelFutureListener.CLOSE);
            ctx.pipeline().remove(this);
        }
    }
}