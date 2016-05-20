package org.mdev.revolution.communication.packets;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class Packet {
    private ByteBuf buffer;
    private short header;
    private int length;

    public boolean isCorrupted() {
        return !buffer.isReadable() || buffer.readableBytes() < 6;
    }

    public Packet(short header, int length, ByteBuf buffer) {
        this.header = header;
        this.length = length;
        this.buffer = buffer;
    }

    public short getHeader() {
        return header;
    }

    public int getLength() {
        return length;
    }

    @Override
    public final String toString() {
        if (buffer == null) {
            return "undefined";
        }
        String buf = buffer.toString(Charset.defaultCharset());
        for (int i = 0; i <= 13; i++) {
            buf = buf.replace(Character.toString((char) i), "[" + i + "]");
        }
        return String.format("%d [%s]", this.header, buf);
    }
}