package fr.orion.core.spigot.common.benchmark;

import com.mongodb.client.model.Filters;
import fr.orion.api.OrionApi;
import fr.orion.api.benchmark.Bench;
import fr.orion.api.benchmark.BenchCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class MongoBench extends BenchCategory {

    public MongoBench() {
        super("mongo");
    }

    @Override
    public void addDefaults() {
        addBenchmarks(getFirstTest(), getSecondTest(), getThirdTest());
    }

    private Bench getFirstTest() {
        return Bench.newBench("test1", bench -> {
            bench.initTimer();

            Flux.range(1, 8).log().doOnNext(this::generate).doOnComplete(bench::notifyEnd).subscribe();
            Flux.range(1, 8).log().doOnNext(this::get).doOnComplete(bench::notifyEnd).subscribe();
        });
    }

    private Bench getSecondTest() {
        return Bench.newBench("test2", bench -> {
            bench.initTimer();

            Flux.from(OrionApi.getProvider().getDatabaseLoader()
                            .getMongoDatabase()
                            .getDatabase()
                            .getCollection("example").find(Filters.exists("id")))
                    .take(10)
                    .publishOn(Schedulers.boundedElastic())
                    .doOnNext(document -> System.out.println("next" + document.get("id")))
                    .toStream()
                    .collect(Collectors.toList())
                    .forEach(document -> System.out.println(">> " + document.toJson()));
        });
    }

    private Bench getThirdTest() {
        return Bench.newBench("test3", bench -> {
            bench.initTimer();

            Flux.range(1, 2000)
                    .log()
                    .doOnNext(this::generate)
                    .publishOn(Schedulers.boundedElastic())
                    .doOnComplete(bench::notifyEnd)
                    .subscribe();
        });
    }

    private void get(int i) {
        Mono.from(OrionApi.getProvider().getDatabaseLoader()
                        .getMongoDatabase()
                        .getDatabase()
                        .getCollection("example")
                        .find(Filters.eq("id", i)).first())
                .publishOn(Schedulers.parallel())
                .subscribe(document -> Bukkit.getPlayer("JoupiterHD").sendMessage("> " + document.get("id")));
    }

    private void generate(int id) {
        Mono.just(new SSS(id))
                .map(SSS::toDoc)
                .publishOn(Schedulers.parallel())
                .subscribe(this::insert);
    }

    private void insert(Document document) {
        Mono.from(OrionApi.getProvider().getDatabaseLoader()
                        .getMongoDatabase()
                        .getDatabase()
                        .getCollection("example")
                        .insertOne(document))
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }

    @Getter
    @AllArgsConstructor
    public static class SSS {

        private final int id;
        private final Map<Integer, AAA> map;

        public SSS(int id) {
            this.id = id;
            this.map = Flux.range(0, 100).collectMap(i -> i, k -> AAA.RANDOM_STRING).block();
        }

        public Document toDoc() {
            return new Document()
                    .append("id", getId())
                    .append("map", getMap());
        }

    }

    @Getter
    @AllArgsConstructor
    enum AAA {

        RANDOM_STRING (RandomStringUtils.randomAlphabetic(999));

        private final String string;

    }

}