package fr.orion.api.rank;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RankRepository {

    Mono<Rank> getRank(String name);

    Flux<Rank> getRanks();

    void addRank(Rank rank);

    void deleteRank(Rank rank);

}
