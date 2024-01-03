package fr.orion.api.benchmark;

import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public abstract class BenchHandler {

    private final ConcurrentMap<String, BenchCategory> categories;

    public BenchHandler() {
        this.categories = new ConcurrentHashMap<>();
        this.load();
    }

    public abstract void load();

    public void addCategory(BenchCategory category) {
        getCategories().put(category.getName(), category);
    }

    public void addCategories(BenchCategory... categories) {
        Arrays.asList(categories).forEach(this::addCategory);
    }

    public void removeCategory(BenchCategory category) {
        removeCategory(category.getName());
    }

    public void removeCategory(String name) {
        getCategories().remove(name);
    }

    public Mono<Optional<BenchCategory>> getCategory(String name) {
        return Mono.just(Optional.ofNullable(getCategories().get(name)));
    }

    public <T> Optional<Bench> getBenchmark(Class<T> clazz) {
        return getCategories().values().stream()
                .map(BenchCategory::getBenchmarks)
                .flatMap(List::stream)
                .filter(clazz::isInstance).findFirst();
    }

    public void run(BenchCategory category, String bench) {
        category.run(bench);
    }

    public void run(String categoryName, String bench) {
        getCategory(categoryName).map(Optional::orElseThrow).subscribe(category -> category.run(bench));
    }

    public void runAll(BenchCategory category) {
        category.runAll();
    }

    public void runAll() {
        Flux.fromIterable(getCategories().values())
                .doOnNext(BenchCategory::runAll)
                .subscribe();
    }

}