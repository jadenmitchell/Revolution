package org.mdev.revolution.monitoring;

@FunctionalInterface
public interface ThreadActivity extends AutoCloseable {
    @Override
    void close();
}