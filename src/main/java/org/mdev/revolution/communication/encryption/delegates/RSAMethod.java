package org.mdev.revolution.communication.encryption.delegates;

import java.math.BigInteger;

@FunctionalInterface
public interface RSAMethod {
    BigInteger invoke(BigInteger input);
}
