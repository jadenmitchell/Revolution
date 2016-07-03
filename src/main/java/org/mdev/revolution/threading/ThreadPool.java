package org.mdev.revolution.threading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class ThreadPool {
    private static final Logger logger = LogManager.getLogger(ThreadPool.class);

    public static int getRecommendedThreads() {
        return (Runtime.getRuntime().availableProcessors() * 2) + 1;
    }

    private final ExecutorService defaultExecutor;
    private final ScheduledExecutorService scheduledExecutor;

    public ThreadPool() {
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        defaultExecutor = new ThreadPoolExecutor(3, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), threadFactory, rejectionHandler);
        scheduledExecutor  = Executors.newScheduledThreadPool(2);
    }

    public ThreadPool(ExecutorService executor) {
        defaultExecutor = executor;
        scheduledExecutor = Executors.newScheduledThreadPool(2);
    }

    public void submit(Runnable task) {
        final long start = System.currentTimeMillis();
        Future future = defaultExecutor.submit(task);

        try {
            future.get(5, TimeUnit.SECONDS);
        } catch (final TimeoutException | InterruptedException | ExecutionException e) {
            future.cancel(true);
        } finally {
            final long end = System.currentTimeMillis();
            logger.info("Time taken to execute packet: " + (start - end) + "ms");
        }
    }

    public void submit(Runnable task, long delay) {
        scheduledExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public void dispose() {
        //logger.debug(ThreadMonitor.getExecutorActivity(defaultExecutor));
        scheduledExecutor.shutdown();
        defaultExecutor.shutdown();
        try {
            final boolean done = defaultExecutor.awaitTermination(1, TimeUnit.MINUTES);
            while(!done) {
                // do nothing.
                logger.info("Shutting down the ThreadPool::defaultExecutor");
            }

            defaultExecutor.shutdownNow();
        }
        catch(final InterruptedException e) {
            logger.error("Unable to terminate the ThreadPool::defaultExecutor", e);
            e.printStackTrace();
        }
    }
}
