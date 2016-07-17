package org.mdev.revolution.communication.encryption.delegation;

import java.math.BigInteger;

@FunctionalInterface
public interface Calculator {
    BigInteger solve(BigInteger input);
}