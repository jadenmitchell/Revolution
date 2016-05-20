package org.mdev.revolution.monitoring;

import com.google.common.annotations.Beta;
import gnu.trove.map.hash.THashMap;
import org.mdev.revolution.monitoring.impl.SingleThreadMonitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Beta
public class ThreadMonitor {
    private static final THashMap<Thread, SingleThreadMonitor> SINGLE_THREAD_MONITORS = new THashMap<>();
    private static final THashMap<String, ExecutorService> AVAILABLE_EXECUTORS = new THashMap<>();

    public static String getExecutorActivity(ExecutorService executor) {
        if (executor instanceof ThreadPoolExecutor) {
            return executor.getClass().getSimpleName() + " -> " +
                    "CURRENT POOL SIZE: " + ((ThreadPoolExecutor) executor).getPoolSize() + " " +
                    "CURRENT TASK COUNT: " + ((ThreadPoolExecutor) executor).getActiveCount() + " " +
                    "COMPLETED TASK COUNT: " + ((ThreadPoolExecutor) executor).getTaskCount();
        }

        return "not supported";
    }
}
