package fr.orion.core.spigot.common.benchmark;

import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ReactorBench extends BenchCategory {

    public ReactorBench() {
        super("reactor");
    }

    @Override
    public void addDefaults() {
        addBenchmark(getFirstTest());
    }

    private Bench getFirstTest() {
        return Bench.newBench("test1", bench ->
                Flux.range(1, 1000000)
                        .flatMap(integer -> Flux.just(getThreadNameFormatted() + "#" + integer))
                        .doOnComplete(bench::notifyEnd)
                        .subscribeOn(Schedulers.boundedElastic())
                        .subscribe(bench::notify));
    }

}
