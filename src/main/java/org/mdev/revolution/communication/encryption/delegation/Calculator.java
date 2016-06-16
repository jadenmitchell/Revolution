package org.mdev.revolution.communication.encryption.delegation;

import java.math.BigInteger;

@FunctionalInterface
public interface Calculator {
    abstract BigInteger solve(BigInteger input);
}