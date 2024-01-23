package fr.orion.api.server;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServerRepository {

    Mono<Server> getServer(String name);

    Flux<Server> getServers();

}
