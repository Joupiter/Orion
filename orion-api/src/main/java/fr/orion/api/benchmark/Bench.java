package fr.orion.api.benchmark;

import fr.orion.api.utils.StopWatch;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

@Getter
@Setter
public abstract class Bench {

    static Logger logger = LoggerFactory.getLogger(Bench.class);

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
        logger.info("[Benchmark] ({}) : {}", getName(), message);
    }

    public void notifyEnd() {
        getStopWatch().stop();
        getStopWatch().log("[Benchmark] (" + getName() + ") :");
    }

}