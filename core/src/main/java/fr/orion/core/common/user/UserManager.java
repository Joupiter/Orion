package fr.orion.core.common.user;

import fr.orion.api.OrionApi;
import fr.orion.api.user.User;
import fr.orion.api.user.UserRepository;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
public class UserManager implements UserRepository {

    private final OrionApi api;

    private final ConcurrentMap<UUID, User> users;

    public UserManager(OrionApi api) {
        this.api = api;
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<User> getUser(UUID id) {
        return Mono.justOrEmpty(users.computeIfAbsent(id, k -> new User(id, 0)));
    }

    @Override
    public Flux<User> getUsers() {
        return Flux.fromIterable(users.values());
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
