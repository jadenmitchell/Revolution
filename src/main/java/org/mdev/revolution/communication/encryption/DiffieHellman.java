package org.mdev.revolution.communication.encryption;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    private static final int BIT_LENGTH = 32;

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
        Random rand = new Random();
        while (publicKey.equals(BigInteger.ZERO)) {
            if (!ignoreBaseKeys) {
                prime = BigInteger.probablePrime(BIT_LENGTH, rand);
                generator = BigInteger.probablePrime(BIT_LENGTH, rand);
            }

            byte[] bytes = new byte[BIT_LENGTH / 8];
            rand.nextBytes(bytes);
            privateKey = new BigInteger(bytes);

            if (generator.compareTo(prime) == 1) {
                BigInteger temp = prime;
                prime = generator;
                generator = temp;
            }
            publicKey = generator.modPow(privateKey, prime);
            if (!ignoreBaseKeys) {
                break;
            }
        }
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