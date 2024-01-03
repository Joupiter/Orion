package fr.orion.api.benchmark;

import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class BenchCategory {

    private final String name;
    private final List<Bench> benchmarks;

    public BenchCategory(String name) {
        this.name = name;
        this.benchmarks = new ArrayList<>();
        this.addDefaults();
    }

    public abstract void addDefaults();

    public void addBenchmark(Bench bench) {
        getBenchmarks().add(bench);
    }

    public void addBenchmark(String name, Runnable runnable) {
        addBenchmark(Bench.newBench(name, runnable));
    }
    public void addBenchmarks(Bench... bench) {
        Arrays.asList(bench).forEach(this::addBenchmark);
    }

    public void removeBenchmark(Bench bench) {
        getBenchmarks().remove(bench);
    }

    public void removeBenchmark(String name) {
        getBenchmark(name).map(Optional::orElseThrow).subscribe(getBenchmarks()::remove);
    }

    /*public Optional<Bench> getBenchmark(String name) {
        return getBenchmarks().stream().filter(bench -> bench.getName().equals(name)).findFirst();
    }*/

    public Mono<Optional<Bench>> getBenchmark(String name) {
        return Mono.just(getBenchmarks().stream().filter(bench -> bench.getName().equals(name)).findFirst());
    }


    public void run(String name) {
        getBenchmark(name).map(Optional::orElseThrow).subscribe(this::run);
    }

    public void run(Bench bench) {
        Mono.just(bench).doOnNext(this::notify).subscribe(Bench::test);
    }

    public void runAll() {
        Flux.fromIterable(getBenchmarks()).subscribe(this::run);
    }

    private void notify(Bench bench) {
        System.out.println("[Benchmark] (" + getName() + ") START: " + bench.getName());
    }

    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    public String getThreadNameFormatted() {
        return "[" + getThreadName() + "] ";
    }

}
