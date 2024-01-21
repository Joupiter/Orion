package fr.orion.core.common.user;

import fr.orion.api.user.User;
import fr.orion.api.user.UserRepository;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class InMemoryUserManager implements UserRepository {

    private final ConcurrentMap<UUID, User> cache;

    public InMemoryUserManager() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<User> getUser(UUID id) {
        return Mono.justOrEmpty(getCache().computeIfAbsent(id, k -> new OrionUser(id))).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<User> getUsers() {
        return Flux.fromIterable(getCache().values()).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public void saveUser(User user) {
        getCache().put(user.getUuid(), user);
    }

    @Override
    public void updateUser(User user) {
        getCache().compute(user.getUuid(), (k, v) -> user);
    }

    @Override
    public void deleteUser(User user) {
        getCache().remove(user.getUuid());
    }

}