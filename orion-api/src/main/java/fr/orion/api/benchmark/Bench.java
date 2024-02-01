package fr.orion.api.benchmark;

import fr.orion.api.utils.StopWatch;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Getter
@Setter
@Slf4j
public abstract class Bench {

    private final String name;
    private final StopWatch stopWatch;

    public Bench(String name) {
        this.name = name;
        this.stopWatch = new StopWatch();
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
            }
        };
    }

    public void notify(String message) {
        log.info("[Benchmark] ({}) : {}", getName(), message);
    }

    public void notifyEnd() {
        getStopWatch().stop();
        getStopWatch().log("[Benchmark] (" + getName() + ") :");
    }

}