package fr.orion.core.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import reactor.core.publisher.Flux;

public class ReactorBench extends BenchCategory {

    public ReactorBench() {
        super("reactor");
    }

    @Override
    public void addDefaults() {
        addBenchmark(new Bench() {
            @Override
            public String getName() {
                return "test1";
            }

            @Override
            public void test() {
                Flux.range(1, 600).map(integer -> integer + "#").subscribe(this::notify, Throwable::printStackTrace);
            }
        });
    }


}
