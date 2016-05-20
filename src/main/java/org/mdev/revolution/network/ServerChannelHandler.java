package org.mdev.revolution.network;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.mdev.revolution.Revolution;

@ChannelHandler.Sharable
public class ServerChannelHandler extends ChannelInboundHandlerAdapter {
    public static final ServerChannelHandler INSTANCE = new ServerChannelHandler();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Revolution.getInstance().getSessionManager().addSession(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Revolution.getInstance().getSessionManager().removeSession(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
    }

    @Override
    @SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}