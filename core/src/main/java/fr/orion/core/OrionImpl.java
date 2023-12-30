package fr.orion.core;

import fr.orion.api.OrionApi;
import fr.orion.api.benchmark.BenchHandler;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import fr.orion.core.common.benchmark.BenchManager;
import fr.orion.core.common.database.DatabaseManager;
import fr.orion.core.common.rank.InMemoryRankManager;
import fr.orion.core.common.user.InMemoryUserManager;

public class OrionImpl implements OrionApi {

    private final DatabaseLoader databaseLoader;

    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    private final BenchHandler benchHandler;

    public OrionImpl() {
        this.databaseLoader = new DatabaseManager();
        this.userRepository = new InMemoryUserManager();
        this.rankRepository = new InMemoryRankManager();
        this.benchHandler = new BenchManager();
    }

    @Override
    public DatabaseLoader getDatabaseLoader() {
        return databaseLoader;
    }

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public RankRepository getRankRepository() {
        return rankRepository;
    }

    @Override
    public BenchHandler getBenchHandler() {
        return benchHandler;
    }

}
