package fr.orion.core.common.user;

import fr.orion.api.user.User;
import fr.orion.api.user.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryUserManager implements UserRepository {

    private final ConcurrentMap<UUID, User> users;

    public InMemoryUserManager() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<User> getUser(UUID id) {
        return Mono.justOrEmpty(users.computeIfAbsent(id, k -> new User(id, 0))).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<User> getUsers() {
        return Flux.fromIterable(users.values()).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getUuid(), user);
    }

    @Override
    public void updateUser(User user) {
        users.compute(user.getUuid(), (k, v) -> user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.getUuid());
    }

}