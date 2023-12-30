package fr.orion.api;

import fr.orion.api.benchmark.BenchHandler;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import lombok.Getter;
import lombok.Setter;

public interface OrionApi {

    DatabaseLoader getDatabaseLoader();

    UserRepository getUserRepository();

    RankRepository getRankRepository();

    BenchHandler getBenchHandler();

    static OrionApi getProvider() {
        return Provider.getProvider();
    }

    static void setProvider(OrionApi api) {
        Provider.setProvider(api);
    }

    class Provider {

        @Getter @Setter
        private static OrionApi provider;

    }

}