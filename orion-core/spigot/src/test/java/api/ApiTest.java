package api;

import fr.orion.api.OrionApi;
import fr.orion.api.division.Division;
import fr.orion.api.rank.Rank;
import fr.orion.api.user.User;
import fr.orion.api.utils.StopWatch;
import fr.orion.core.common.rank.OrionRank;
import fr.orion.core.common.user.Account;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class ApiTest {

    static Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() {
        StopWatch stopWatch = new StopWatch(true);

        OrionApi.setProvider(new OrionTestImpl());
        UUID uuid = UUID.randomUUID();
        User user = new Account(uuid);

        Rank defaultRank = new OrionRank("Joueur", "&7Joueur", 0, true, Collections.emptySet());
        Rank adminRank = new OrionRank("Admin", "&cAdmin", 99, true, Set.of("orion.*"));


        logger.info("-------------------------");

        IntStream.rangeClosed(0, 9999)
                .forEach(value -> OrionApi.getProvider().getUserRepository().saveUser(new Account(UUID.randomUUID())));

        OrionApi.getProvider().getUserRepository().saveUser(user);
        OrionApi.getProvider().getUserRepository().getUsers().map(User::toString).subscribe(logger::info);
        logger.info("-------------------------");
        OrionApi.getProvider().getUserRepository().getUser(uuid).subscribe(this::addCoins);
        OrionApi.getProvider().getUserRepository().deleteUser(user);
        OrionApi.getProvider().getUserRepository().getUser(uuid).doOnError(Throwable::printStackTrace).map(User::toString).subscribe(logger::info);
        logger.info("-------------------------");

        OrionApi.getProvider().getRankRepository().addRank(defaultRank);
        OrionApi.getProvider().getRankRepository().addRank(adminRank);

        OrionApi.getProvider().getRankRepository().getRanks().subscribe(rank -> rank.sendInformation(logger));

        logger.info("-------------------------");
        Flux<Division> flux = Flux.fromIterable(Division.getDivisions());
        flux.subscribe(System.out::println);
        logger.info("-------------------------");
        stopWatch.stopAndLog();
    }

    public void addCoins(User user) {
        //user.getCoins().add(1);
        System.out.println(user);
    }

}