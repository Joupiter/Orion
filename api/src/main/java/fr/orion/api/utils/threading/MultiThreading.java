package fr.orion.api.utils.threading;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class MultiThreading {

    private final AtomicInteger counter = new AtomicInteger(0);

    public final ExecutorService pool = Executors.newFixedThreadPool(10, runnable -> new Thread(runnable, String.format("Thread %s", counter.incrementAndGet())));

    public final ScheduledExecutorService runnablePool = Executors.newScheduledThreadPool(5, runnable -> new Thread(runnable, String.format("Thread %s", counter.incrementAndGet())));

    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return runnablePool.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, TimeUnit unit) {
        return runnablePool.schedule(r, initialDelay, unit);
    }

    public void runAsync(Runnable runnable) {
        pool.execute(runnable);
    }

    public int getTotal() {
        return Integer.sum(((ThreadPoolExecutor) pool).getActiveCount(), ((ThreadPoolExecutor) runnablePool).getActiveCount());
    }

    public void stopTask() {
        pool.shutdown();
        runnablePool.shutdown();
    }

}