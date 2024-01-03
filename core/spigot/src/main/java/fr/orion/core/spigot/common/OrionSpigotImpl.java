package fr.orion.core.spigot.common;

import fr.orion.api.benchmark.BenchHandler;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import fr.orion.core.spigot.common.api.OrionSpigotApi;
import fr.orion.core.common.rank.InMemoryRankManager;
import fr.orion.core.common.user.InMemoryUserManager;
import fr.orion.core.common.benchmark.BenchManager;
import fr.orion.core.common.database.DatabaseManager;
import fr.orion.core.spigot.common.benchmark.ReactorBench;
import fr.orion.core.spigot.common.benchmark.RedisBench;

public class OrionSpigotImpl extends OrionSpigotApi {

    private final DatabaseLoader databaseLoader;

    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    private final BenchHandler benchHandler;

    public OrionSpigotImpl() {
        this.databaseLoader = new DatabaseManager();
        this.userRepository = new InMemoryUserManager();
        this.rankRepository = new InMemoryRankManager();
        this.benchHandler = new BenchManager();
    }

    @Override
    public void load() {
        getDatabaseLoader().connect();
        getBenchHandler().addCategories(new RedisBench(), new ReactorBench());
    }

    @Override
    public void unload() {
        getDatabaseLoader().disconnect();
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
