package org.mdev.revolution.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.mdev.revolution.communication.packets.outgoing.ServerPacket;

import java.util.List;

public class PacketEncoder extends MessageToMessageEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object data, List<Object> out) throws Exception {
        if (!ctx.channel().isActive())
            return;

        if (data instanceof ServerPacket) {
            out.add(((ServerPacket) data).getBuffer());
        }
        else if (data instanceof ByteBuf) {
            out.add(data);
        }
        else if (data instanceof String) {
            out.add(((String) data).getBytes());
        }
    }
}