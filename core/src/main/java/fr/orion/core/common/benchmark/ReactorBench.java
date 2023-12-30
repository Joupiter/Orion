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

            private long start;

            @Override
            public String getName() {
                return "test1";
            }

            @Override
            public void test() {
                start = System.currentTimeMillis();
                Flux.range(1, 100000).map(integer -> integer + "#").doOnComplete(this::complete).subscribe(this::notify, Throwable::printStackTrace);
            }

            private void complete() {
                notify("END IN " + (System.currentTimeMillis() - start));
            }

        });
    }


}
