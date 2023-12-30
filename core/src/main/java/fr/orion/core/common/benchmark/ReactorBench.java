package fr.orion.core.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

public class ReactorBench extends BenchCategory {

    public ReactorBench() {
        super("reactor");
    }

    @Override
    public void addDefaults() {
        addBenchmarks(new Bench() {

            private long start;

            @Override
            public String getName() {
                return "test1";
            }

            @Override
            public void test() {
                start = System.currentTimeMillis();
                Flux.range(1, 1000000).flatMap(integer -> Flux.just(getThreadNameFormatted() + "#" + integer))
                        .doOnComplete(this::complete)
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe(this::notify);
            }

            private void complete() {
                notify("END IN " + (System.currentTimeMillis() - start));
            }

        }, new Bench() {

            @Override
            public String getName() {
                return "test2";
            }

            @Override
            public void test() {
                long s = System.currentTimeMillis();
                IntStream.rangeClosed(1, 1000000).mapToObj(integer -> getThreadNameFormatted() + "#" + integer).forEach(this::notify);
                notify("END IN " + (System.currentTimeMillis() - s));
            }

        });
    }

}
