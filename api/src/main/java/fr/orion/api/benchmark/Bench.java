package fr.orion.api.benchmark;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public abstract class Bench {

    private final String name;
    private final long startTime;

    public Bench(String name) {
        this.name = name;
        this.startTime = System.currentTimeMillis();
    }

    public abstract void test();

    public static Bench newBench(String name, Runnable runnable) {
        return newBench(name, bench -> runnable.run());
    }

    public static Bench newBench(String name, Consumer<Bench> consumer) {
        return new Bench(name) {
            @Override
            public void test() {
                consumer.accept(this);
                notifyEnd();
            }
        };
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
