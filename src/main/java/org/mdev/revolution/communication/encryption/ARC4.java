package org.mdev.revolution.communication.encryption;

public class ARC4 {
    public static final int POOL_SIZE = 256;

    private int i;
    private int j;

    private final byte[] bytes;

    public ARC4() {
        bytes = new byte[POOL_SIZE];
    }

    public void initialize(byte[] key) {
        i = 0;
        j = 0;

        for (i = 0; i < POOL_SIZE; ++i) {
            bytes[i] = (byte) i;
        }

        for (i = 0; i < POOL_SIZE; ++i) {
            j = (j + bytes[i] + key[i % key.length]) & (POOL_SIZE - 1);
            swap(i, j);
        }

        i = 0;
        j = 0;
    }

    private void swap(int a, int b) {
        byte t = bytes[a];
        bytes[a] = bytes[b];
        bytes[b] = t;
    }

    public byte next() {
        i = ++i & (POOL_SIZE - 1);
        j = (j + bytes[i]) & (POOL_SIZE - 1);
        swap(i, j);
        return bytes[(bytes[i] + bytes[j]) & 255];
    }

    public byte[] encrypt(byte[] src) {
        for (int k = 0; k < src.length; k++) {
            src[k] ^= next();
        }
        return src;
    }

    public byte[] decrypt(byte[] src) {
        return encrypt(src);
    }
}
