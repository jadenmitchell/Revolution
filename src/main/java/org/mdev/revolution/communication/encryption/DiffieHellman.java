package org.mdev.revolution.communication.encryption;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    private static final int BIT_LENGTH = 30;

    private BigInteger prime;
    private BigInteger generator;

    private BigInteger privateKey = BigInteger.ZERO;
    private BigInteger publicKey = BigInteger.ZERO;

    public DiffieHellman() {
        initialize(false);
    }

    public DiffieHellman(BigInteger prime, BigInteger generator) {
        this.prime = prime;
        this.generator = generator;
        initialize(true);
    }

    private void initialize(boolean ignoreBaseKeys) {
        if (!ignoreBaseKeys) {
            Random rand = new Random();
            prime = BigInteger.probablePrime(BIT_LENGTH, rand);
            generator = BigInteger.probablePrime(BIT_LENGTH, rand);
        }

        privateKey = new BigInteger(generateRandomHexString(BIT_LENGTH), 16);

        if (generator.intValue() > prime.intValue()) {
            BigInteger temp = prime;
            prime = generator;
            generator = temp;
        }

        publicKey = generator.modPow(privateKey, prime);
    }

    private static String generateRandomHexString(int length) {
        int key;
        String result = "";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            key = 1 + (int) (rand.nextDouble() * 254.0D);
            result = result + Integer.toString(key, 16);
        }
        return result;
    }

    public BigInteger getPrime() {
        return prime;
    }

    public BigInteger getGenerator() {
        return generator;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger calculateSharedKey(BigInteger m) {
        return m.modPow(privateKey, prime);
    }
}