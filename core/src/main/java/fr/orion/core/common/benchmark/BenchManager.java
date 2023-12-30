package fr.orion.core.common.benchmark;

import fr.orion.api.benchmark.BenchHandler;

public class BenchManager extends BenchHandler {

    @Override
    public void load() {
        addCategories(new RedisBench(), new ReactorBench());
    }

}
