package fr.orion.core.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;

public class RedisBench extends BenchCategory {

    public RedisBench() {
        super("redis");
    }

    @Override
    public void addDefaults() {
        addBenchmarks(new RedisExampleBench(), new RedisGetBench());
    }

    static class RedisExampleBench implements Bench {

        @Override
        public String getName() {
            return "first";
        }

        @Override
        public void test() {
            System.out.println("first testing");
        }

    }

    static class RedisGetBench implements Bench {

        @Override
        public String getName() {
            return "second";
        }

        @Override
        public void test() {
            System.out.println("second testing");
        }

    }

}
