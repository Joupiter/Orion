package fr.orion.api.user;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public interface UserRepository {

    ConcurrentMap<UUID, User> getCache();

    Mono<User> getUser(UUID id);

    Flux<User> getUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

}