package fr.orion.core.common.rank;

import fr.orion.api.rank.Rank;
import fr.orion.api.rank.RankRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRankManager implements RankRepository {

    private final List<Rank> ranks;

    public InMemoryRankManager() {
        this.ranks = new ArrayList<>();
    }

    @Override
    public Mono<Rank> getRank(String name) {
        return Mono.justOrEmpty(ranks.stream().filter(rank -> rank.getName().equals(name)).findFirst()).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Rank> getRanks() {
        return Flux.fromIterable(ranks).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public void addRank(Rank rank) {
        ranks.add(rank);
    }

    @Override
    public void deleteRank(Rank rank) {
        ranks.remove(rank);
    }

}