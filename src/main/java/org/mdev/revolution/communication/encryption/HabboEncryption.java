package org.mdev.revolution.communication.encryption;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class HabboEncryption {
    private static RSAKey rsaKey;
    private static DiffieHellman diffieHellman;

    public static void initialize(String n, String e, String d) {
        rsaKey = new RSAKey(new BigInteger(n, 16), new BigInteger(e, 16), new BigInteger(d, 16),
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);
        diffieHellman = new DiffieHellman();
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    private static String getRsaStringEncrypted(String msg) {
        byte[] m = msg.getBytes();
        byte[] c = rsaKey.sign(m);
        return toHexString(c);
    }

    public static String getRsaDiffieHellmanPrimeKey() {
        String key = diffieHellman.getPrime().toString(10);
        return getRsaStringEncrypted(key);
    }

    public static String getRsaDiffieHellmanGeneratorKey() {
        String key = diffieHellman.getGenerator().toString(10);
        return getRsaStringEncrypted(key);
    }

    public static String GetRsaDiffieHellmanPublicKey() {
        String key = diffieHellman.getPublicKey().toString(10);
        return getRsaStringEncrypted(key);
    }

    public static BigInteger calculateDiffieHellmanSharedKey(String publicKey) {
        byte[] cbytes = toByteArray(publicKey);
        byte[] publicKeyBytes = rsaKey.verify(cbytes);
        String publicKeyString = new String(publicKeyBytes, StandardCharsets.UTF_8);
        return diffieHellman.calculateSharedKey(new BigInteger(publicKeyString, 10));
    }
}