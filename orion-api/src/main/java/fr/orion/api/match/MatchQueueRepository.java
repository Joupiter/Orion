package fr.orion.api.match;

import reactor.core.publisher.Flux;

public interface MatchQueueRepository {

    Flux<Match> getQueues();

}