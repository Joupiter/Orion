package fr.orion.core.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;

public class RedisBench extends BenchCategory {

    public RedisBench() {
        super("redis");
    }

    @Override
    public void addDefaults() {
        addBenchmarks(getRedisExampleBench(), getRedisGetBench());
    }

    private Bench getRedisExampleBench() {
        return Bench.newBench("first", bench -> bench.notify("first testing"));
    }

    private Bench getRedisGetBench() {
        return Bench.newBench("second", bench -> bench.notify("second testing"));
    }

}
