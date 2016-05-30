package org.mdev.revolution.network.sessions;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.encryption.ARC4;
import org.mdev.revolution.communication.packets.outgoing.ServerPacket;
import org.mdev.revolution.network.codec.EncryptionDecoder;

public class Session {
    private static final Logger logger = LogManager.getLogger(Session.class);

    private Channel channel;
    private ARC4 rc4;

    public Channel getChannel() {
        return channel;
    }

    public Session(Channel channel) {
        this.channel = channel;
    }

    public ChannelFuture sendPacket(ServerPacket packet) {
        ChannelFuture future = channel.writeAndFlush(packet);
        if (!future.isSuccess()) {
            logger.error("Failed to send packet: " + packet.getHeader(), future.cause());
            future.cause().printStackTrace();
            //throw future.cause();
        }
        return future;
    }

    public void sendQueued(ServerPacket packet) {
        channel.write(packet).addListener(ChannelFutureListener.CLOSE);
    }

    public void enableRC4(byte[] sharedKey) {
        rc4 = new ARC4();
        rc4.initialize(sharedKey);
        channel.pipeline().addBefore("packetDecoder", "packetCrypto", new EncryptionDecoder());
    }

    public void disconnect() {
        if (channel != null) {
            channel.disconnect();
            channel = null;
        }
    }
}