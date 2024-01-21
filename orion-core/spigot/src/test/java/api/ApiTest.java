package api;

import fr.orion.api.OrionApi;
import fr.orion.api.division.Division;
import fr.orion.api.division.DivisionTier;
import fr.orion.api.rank.Rank;
import fr.orion.api.user.User;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class ApiTest {

    @Test
    public void test() {
        OrionApi.setProvider(new OrionTestImpl());
        UUID uuid = UUID.randomUUID();
        User user = new User(uuid, 0);

        Rank defaultRank = new Rank("Joueur", "&7Joueur", 0, true, Collections.emptyList());
        Rank adminRank = new Rank("Admin", "&cAdmin", 99, true, List.of("orion.*"));

        System.out.println("-------------------------");

        IntStream.rangeClosed(0, 9999)
                .forEach(value -> OrionApi.getProvider().getUserRepository().saveUser(new User(UUID.randomUUID(), 0)));

        OrionApi.getProvider().getUserRepository().saveUser(user);
        OrionApi.getProvider().getUserRepository().getUsers().subscribe(System.out::println);
        System.out.println("-------------------------");
        OrionApi.getProvider().getUserRepository().getUser(uuid).subscribe(this::addCoins);
        OrionApi.getProvider().getUserRepository().deleteUser(user);
        OrionApi.getProvider().getUserRepository().getUser(uuid).doOnError(Throwable::printStackTrace).subscribe(System.out::println);
        System.out.println("-------------------------");

        OrionApi.getProvider().getRankRepository().addRank(defaultRank);
        OrionApi.getProvider().getRankRepository().addRank(adminRank);

        OrionApi.getProvider().getRankRepository().getRanks().subscribe(System.out::println);

        System.out.println("-------------------------");
        List<Division> divisions = List.of(new Division("Challenger", Set.of(DivisionTier.I)), new Division("Bronze", Set.of(DivisionTier.values())));
        Flux<Division> flux = Flux.fromIterable(divisions);

        flux.subscribe(System.out::println);
        System.out.println("-------------------------");
    }

    public void addCoins(User user) {
        user.setCoins(user.getCoins() + 1);
        System.out.println(user);
    }

}