package org.mdev.revolution.communication.encryption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mdev.revolution.communication.encryption.delegation.Calculator;

import java.math.BigInteger;
import java.util.Random;

public class RSAKey {
    private static final Logger logger = LogManager.getLogger(RSAKey.class);

    private BigInteger n;
    private BigInteger e;
    private BigInteger d;
    private BigInteger p;
    private BigInteger q;
    private BigInteger dmp1;
    private BigInteger dmq1;
    private BigInteger coeff;

    protected boolean canDecrypt;
    protected boolean canEncrypt;

    public RSAKey(BigInteger n, BigInteger e, BigInteger d, BigInteger p, BigInteger q,
                  BigInteger dmp1, BigInteger dmq1, BigInteger coeff) {
        this.n = n;
        this.e = e;
        this.d = d;
        this.p = p;
        this.q = q;
        this.dmp1 = dmp1;
        this.dmq1 = dmq1;
        this.coeff = coeff;
    }

    public void generatePair(int b, BigInteger e) {
        this.e = e;
        int qs = b >> 1;

        while (true) {
            while (true) {
                p = BigInteger.probablePrime(b - qs, new Random());

                if (p.subtract(BigInteger.ONE).gcd(e).equals(BigInteger.ONE) && p.isProbablePrime(10)) {
                    break;
                }
            }

            while (true) {
                q = BigInteger.probablePrime(qs, new Random());

                if (q.subtract(BigInteger.ONE).gcd(e).equals(BigInteger.ONE) && q.isProbablePrime(10)) {
                    break;
                }
            }

            if (q.compareTo(p) == 1) {
                BigInteger t = p;
                p = q;
                q = t;
            }

            BigInteger phi = p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));

            if (phi.gcd(e).equals(BigInteger.ONE)) {
                n = p.multiply(q);
                d = e.modInverse(phi);
                dmp1 = d.remainder(p.subtract(BigInteger.ONE));
                dmq1 = d.remainder(p.subtract(BigInteger.ONE));
                coeff = q.modInverse(p);
            }

            break;
        }

        canEncrypt = (n.compareTo(BigInteger.ZERO) == 1 && e.compareTo(BigInteger.ZERO) == 1);
        canDecrypt = (canEncrypt && d.compareTo(BigInteger.ZERO) == 1);

        System.out.println(n.toString(16));
        System.out.println(d.toString(16));
    }

    public int getBlockSize() {
        return (n.bitLength() + 7) / 8;
    }

    public byte[] encrypt(byte[] src) {
        return doEncrypt(this::doPublic, src);
    }

    public byte[] decrypt(byte[] src) {
        return doDecrypt(this::doPublic, src);
    }

    public byte[] sign(byte[] src) {
        return doEncrypt(this::doPrivate, src);
    }

    public byte[] verify(byte[] src) {
        return doDecrypt(this::doPrivate, src);
    }

    private byte[] doEncrypt(Calculator cd, byte[] src) {
        int bl = getBlockSize();
        byte[] pBytes = pkcs1pad(src, bl);
        BigInteger m = new BigInteger(pBytes);

        if (m.equals(BigInteger.ZERO)) {
            return null;
        }

        BigInteger c = cd.solve(m);

        if (c.equals(BigInteger.ZERO)) {
            return null;
        }

        return c.toByteArray();
    }

    private byte[] doDecrypt(Calculator cd, byte[] src) {
        BigInteger c = new BigInteger(src);
        BigInteger m = cd.solve(c);

        if (m.equals(BigInteger.ZERO)) {
            return null;
        }

        int bl = getBlockSize();
        return pkcs1unpad(m.toByteArray(), bl);
    }

    private byte[] pkcs1pad(byte[] src, int n) {
        byte[] bytes = new byte[n];

        int i = src.length - 1;
        while (i >= 0 && n > 11) {
            bytes[--n] = src[i--];
        }

        bytes[--n] = 0;
        while (n > 2) {
            byte x = (byte) 0xFF;
            bytes[--n] = x;
        }

        bytes[--n] = (byte) 1;
        bytes[--n] = 0;
        return bytes;
    }

    private byte[] pkcs1unpad(byte[] src, int n) {
        int i = 0;
        while (i < src.length && src[i] == 0) {
            ++i;
        }
        if (src.length - i != n - 1 || src[i] > 2) {
            String hex = Integer.toHexString(src[i]  & 0xFF);
            logger.error("PKCS#1 unpad: i={}, expected src[i]==[0,1,2], got src[i]={}", i, hex);
            return null;
        }
        ++i;
        while (src[i] != 0) {
            if (++i >= src.length) {
                String hex = Integer.toHexString(src[i - 1]  & 0xFF);
                logger.error("PKCS#1 unpad: i={}, src[i-1]!=0 (={})", i, hex);
            }
        }
        byte[] bytes = new byte[src.length - i - 1];
        for (int p = 0; ++i < src.length; p++) {
            bytes[p] = src[i];
        }
        return bytes;
    }

    protected BigInteger doPublic(BigInteger m) {
        return m.modPow(e, n);
    }

    protected BigInteger doPrivate(BigInteger m) {
        return m.modPow(d, n);
    }
}
