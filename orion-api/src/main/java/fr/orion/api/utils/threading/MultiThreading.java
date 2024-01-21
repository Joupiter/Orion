package fr.orion.api.utils.threading;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class MultiThreading {

    private final AtomicInteger counter = new AtomicInteger(0);

    public final ExecutorService pool = Executors.newFixedThreadPool(10, runnable -> new Thread(runnable, String.format("orion-fixed-thread-%s", counter.incrementAndGet())));
    public final ExecutorService cachedPool = Executors.newCachedThreadPool(runnable ->  new Thread(runnable, String.format("orion-cached-thread-%s", counter.incrementAndGet())));

    public final ScheduledExecutorService single = Executors.newSingleThreadScheduledExecutor(runnable -> new Thread(runnable, String.format("orion-single-thread-%s", counter.incrementAndGet())));
    public final ScheduledExecutorService runnablePool = Executors.newScheduledThreadPool(10, runnable -> new Thread(runnable, String.format("orion-scheduled-thread-%s", counter.incrementAndGet())));

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return scheduledFuture(runnablePool, runnable, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, TimeUnit unit) {
        return scheduledFuture(runnablePool, runnable, initialDelay, unit);
    }

    public ScheduledFuture<?> singleSchedule(Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return scheduledFuture(single, runnable, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> singleSchedule(Runnable runnable, long initialDelay, TimeUnit unit) {
        return scheduledFuture(single, runnable, initialDelay, unit);
    }

    public void runSingle(Runnable runnable) {
        single.execute(runnable);
    }

    public void runAsync(Runnable runnable) {
        pool.execute(runnable);
    }

    public void runCached(Runnable runnable) {
        cachedPool.submit(runnable);
    }

    private ScheduledFuture<?> scheduledFuture(ScheduledExecutorService service, Runnable runnable, long initialDelay, long delay, TimeUnit unit) {
        return service.scheduleAtFixedRate(runnable, initialDelay, delay, unit);
    }

    private ScheduledFuture<?> scheduledFuture(ScheduledExecutorService service, Runnable runnable, long initialDelay, TimeUnit unit) {
        return service.schedule(runnable, initialDelay, unit);
    }

    public int getTotal() {
        return counter.get();
    }

    public void shutdown() {
        pool.shutdown();
        cachedPool.shutdown();
        single.shutdown();
        runnablePool.shutdown();
    }

}