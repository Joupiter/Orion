package fr.orion.core.spigot.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;

public class ReactorBench extends BenchCategory {

    public ReactorBench() {
        super("reactor");
    }

    @Override
    public void addDefaults() {
        addBenchmark(getFirstTest());
    }

    private Bench getFirstTest() {
        return Bench.newBench("test1", bench -> {
            bench.getStopWatch().start();
            bench.notifyEnd();
        });
    }

}