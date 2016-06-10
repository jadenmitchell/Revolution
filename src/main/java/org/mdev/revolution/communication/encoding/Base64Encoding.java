package org.mdev.revolution.communication.encoding;

public class Base64Encoding {
    public static byte NEGATIVE = 64; // '@'
    public static byte POSITIVE = 65; // 'A'

    public static byte[] encodeInt(int i, int numBytes) {
        byte[] result = new byte[numBytes];

        for (int j = 1; j <= numBytes; j++) {
            int k = ((numBytes - j) * 6);
            result[j - 1] = (byte)(0x40 + ((i >> k) & 0x3f));
        }

        return result;
    }

    public static int decodeInt(byte[] data) {
        int i = 0;
        int j = 0;

        for (int k = (data.length - 1); k >= 0; k--) {
            int x = (data[k] - 0x40);

            if (j > 0) {
                x *= (int) Math.pow(64.0, (double) j);
            }

            i += x;
            j++;
        }

        return i;
    }
}
