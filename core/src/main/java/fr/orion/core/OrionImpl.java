package fr.orion.core;

import fr.orion.api.OrionApi;
import fr.orion.api.database.DatabaseLoader;
import fr.orion.api.rank.RankRepository;
import fr.orion.api.user.UserRepository;
import fr.orion.core.common.database.DatabaseManager;
import fr.orion.core.common.rank.RankManager;
import fr.orion.core.common.user.UserManager;

public class OrionImpl implements OrionApi {

    private final DatabaseLoader databaseLoader;

    private final UserRepository userRepository;
    private final RankRepository rankRepository;

    public OrionImpl() {
        this.databaseLoader = new DatabaseManager();
        this.userRepository = new UserManager(this);
        this.rankRepository = new RankManager(this);
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

}
