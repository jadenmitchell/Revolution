package org.mdev.revolution.communication.packets.incoming;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.mdev.revolution.communication.packets.Packet;

public class ClientPacket extends Packet {
    private int length;
    private final ByteBuf buffer;

    public ClientPacket(short header, int length, ByteBuf buffer) {
        super(header, length, buffer);
        this.length = length;
        this.buffer = buffer == null ? Unpooled.buffer(6) : buffer;
    }

    public int remaining() {
        return buffer.readableBytes();
    }

    public boolean readBoolean() {
        return (buffer.readByte() == 1);
    }

    public int readInt() {
        return buffer.readInt();
    }

    public short readShort() {
        return buffer.readShort();
    }

    public String readString() {
        short length = readShort();
        byte[] bytes = new byte[length];

        buffer.readBytes(bytes);

        return new String(bytes);
    }
}