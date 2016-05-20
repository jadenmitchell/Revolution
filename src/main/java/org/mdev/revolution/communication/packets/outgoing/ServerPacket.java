package org.mdev.revolution.communication.packets.outgoing;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.mdev.revolution.communication.packets.Packet;

public class ServerPacket extends Packet {
    private ByteBuf buffer;
    private int length;
    private boolean finalized;

    public ServerPacket(short header) {
        super(header, 0, Unpooled.buffer(6));
        length = 0;
        buffer = Unpooled.buffer(6);
        buffer.writeInt(0); // length placeholder
        buffer.writeShort(header);
        finalized = false;
    }

    public void writeBoolean(boolean b) {
        buffer.writeByte(b ? 1 : 0);
    }

    public void writeInt(int i) {
        buffer.writeInt(i);
    }

    public void writeShort(int i) {
        buffer.writeShort(i);
    }

    public void writeString(String s) {
        if (s == null) {
            s = "";
        }

        byte[] dat = s.getBytes();
        buffer.writeShort(dat.length);
        buffer.writeBytes(dat);
    }

    public void writeString(char c) {
        writeString(Character.toString(c));
    }

    public void writeString(int i) {
        writeString(Integer.toString(i));
    }

    public void writeString(float f) {
        writeString(Float.toString(f));
    }

    public int length() {
        return buffer.writerIndex() - 4;
    }

    public ByteBuf getBuffer() {
        if (!finalized) {
            buffer.setInt(0, this.length());
            finalized = true;
        }
        return buffer;
    }
}
