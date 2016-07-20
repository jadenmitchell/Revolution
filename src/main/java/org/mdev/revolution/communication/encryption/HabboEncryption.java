package org.mdev.revolution.communication.encryption;

import org.mdev.revolution.Revolution;
import org.mdev.revolution.utilities.SecurityUtil;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public final class HabboEncryption {
    private static final BigInteger PRIME = new BigInteger("114670925920269957593299136150366957983142588366300079186349531");
    private static final BigInteger GENERATOR = new BigInteger("1589935137502239924254699078669119674538324391752663931735947");

    private static RSA rsa;
    private static DiffieHellman diffieHellman;

    public static void initialize() {
        rsa = new RSA(new BigInteger(Revolution.N, 16), new BigInteger(Revolution.E), new BigInteger(Revolution.D, 16),
                BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);
        // Currently using predefined DH keys
        diffieHellman = new DiffieHellman(PRIME, GENERATOR);
    }

    public static String toHexString(byte[] bytes) {
        return SecurityUtil.bytesToHex(bytes);
    }

    public static byte[] toByteArray(String s) {
        return SecurityUtil.hexToBytes(s);
    }

    private static String getRsaStringEncrypted(String msg) {
        byte[] m = msg.getBytes();
        byte[] c = rsa.sign(m);
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

    public static String getRsaDiffieHellmanPublicKey() {
        String key = diffieHellman.getPublicKey().toString(10);
        return getRsaStringEncrypted(key);
    }

    public static BigInteger calculateDiffieHellmanSharedKey(String publicKey) {
        byte[] cbytes = toByteArray(publicKey);
        byte[] publicKeyBytes = rsa.verify(cbytes);
        String publicKeyString = new String(publicKeyBytes, StandardCharsets.UTF_8);
        return diffieHellman.calculateSharedKey(new BigInteger(publicKeyString, 10));
    }
}
