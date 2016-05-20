package org.mdev.revolution.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.mdev.revolution.communication.encryption.HabboEncryption;

import java.util.List;

public class EncryptionDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        //out.add(HabboEncryption.);
    }
}
