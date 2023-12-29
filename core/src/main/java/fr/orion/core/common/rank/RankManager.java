package fr.orion.core.common.rank;

import fr.orion.api.OrionApi;
import fr.orion.api.rank.Rank;
import fr.orion.api.rank.RankRepository;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RankManager implements RankRepository {

    private final OrionApi api;

    private final List<Rank> ranks;

    public RankManager(OrionApi api) {
        this.api = api;
        this.ranks = new ArrayList<>();
    }

    @Override
    public Mono<Rank> getRank(String name) {
        return Mono.justOrEmpty(ranks.stream().filter(rank -> rank.getName().equals(name)).findFirst());
    }

    @Override
    public Flux<Rank> getRanks() {
        return Flux.fromIterable(ranks);
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
