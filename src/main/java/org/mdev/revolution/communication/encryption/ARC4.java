package org.mdev.revolution.communication.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ARC4 {
    private static final int POOL_SIZE = 256;
    private int i = 0;
    private int j = 0;
    private int[] table;

    public ARC4() {
        table = new int[POOL_SIZE];
    }

    public void init(byte[] key) {
        i = 0;
        j = 0;

        for (i = 0; i < POOL_SIZE; ++i) {
            table[i] = (byte) i;
        }

        for (i = 0; i < POOL_SIZE; ++i) {
            j = (j + table[i] + key[i % key.length]) & (POOL_SIZE - 1);
            swap(i, j);
        }

        i = 0;
        j = 0;
    }

    private void swap(int a, int b) {
        int k = table[a];
        table[a] = table[b];
        table[b] = k;
    }

    private byte next() {
        i = ++i & (POOL_SIZE - 1);
        j = (j + table[i]) & (POOL_SIZE - 1);
        swap(i, j);
        return (byte) table[(table[i] + table[j]) & (POOL_SIZE - 1)];
    }

    public ByteBuf decipher(ByteBuf buf) {
        ByteBuf result = Unpooled.buffer();
        while (buf.isReadable()) {
            result.writeByte((byte) (buf.readByte() ^ next()));
        }
        return result.resetReaderIndex();
    }
}
