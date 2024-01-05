package fr.orion.api.utils.threading;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class MultiThreading {

    private final AtomicInteger counter = new AtomicInteger(0);

    public final ExecutorService pool = Executors.newFixedThreadPool(5, runnable -> new Thread(runnable, String.format("orion-fixed-thread-%s", counter.incrementAndGet())));

    public final ExecutorService cachedPool = Executors.newCachedThreadPool(runnable ->  new Thread(runnable, String.format("orion-cached-thread-%s", counter.incrementAndGet())));

    public final ScheduledExecutorService runnablePool = Executors.newScheduledThreadPool(7, runnable -> new Thread(runnable, String.format("orion-scheduled-thread-%s", counter.incrementAndGet())));


    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return runnablePool.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, TimeUnit unit) {
        return runnablePool.schedule(runnable, initialDelay, unit);
    }

    public void runAsync(Runnable runnable) {
        pool.execute(runnable);
    }

    public void runCached(Runnable runnable) {
        cachedPool.submit(runnable);
    }

    public int getTotal() {
        return (int) Thread.getAllStackTraces().keySet().stream().filter(thread -> thread.getName().startsWith("orion")).count();
    }

    public void stopTask() {
        pool.shutdown();
        cachedPool.shutdown();
        runnablePool.shutdown();
    }

}