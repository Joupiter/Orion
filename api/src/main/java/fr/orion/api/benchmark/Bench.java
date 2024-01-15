package fr.orion.api.benchmark;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Bench {

    private final String name;
    private long startTime;

    public abstract void test();

    public static Bench newBench(String name, Runnable runnable) {
        return newBench(name, bench -> runnable.run());
    }

    public static Bench newBench(String name, Consumer<Bench> consumer) {
        return new Bench(name) {
            @Override
            public void test() {
                consumer.accept(this);
            }
        };
    }

    public void initTimer() {
        setStartTime(System.currentTimeMillis());
    }

    public void notify(String message) {
        System.out.println("[Benchmark] (" + getName() + ") : " + message);
    }

    public void notifyEnd() {
        notify("END IN " + getEndTime());
    }

    public long getEndTime() {
        return System.currentTimeMillis() - getStartTime();
    }

}
