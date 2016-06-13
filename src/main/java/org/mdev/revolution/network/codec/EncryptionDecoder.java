package org.mdev.revolution.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.network.sessions.Session;

import java.util.List;

public class EncryptionDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        Session session = Revolution.getInstance().getSessionManager().getSessionByChannel(ctx.channel());
        out.add(session.getRC4().decipher(buffer));
    }
}