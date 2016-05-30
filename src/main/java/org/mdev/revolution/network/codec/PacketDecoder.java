package org.mdev.revolution.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.Revolution;
import org.mdev.revolution.communication.packets.incoming.ClientPacket;
import org.mdev.revolution.network.sessions.Session;

import java.util.List;

public class PacketDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger logger = LogManager.getLogger(PacketDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        // less than 5 bytes == garbage data
        if (buffer.readableBytes() < 5) {
            return;
        }

        int length = buffer.readInt();
        /*if (length > 5120 && (length >> 24 != 60)) {
            ctx.close();
        }*/
        short header = buffer.readShort();

        Session session = Revolution.getInstance().getSessionManager().getSessionByChannel(ctx.channel());
        ClientPacket packet = new ClientPacket(header, length, buffer);
        Revolution.getInstance().getPacketManager().invoke(session, packet);
    }
}