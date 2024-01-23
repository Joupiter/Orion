package fr.orion.api.rank;

import org.slf4j.Logger;

import java.util.Set;
import java.util.function.Predicate;

public interface Rank {

    String getName();

    String getPrefix();

    int getPower();

    boolean isDefaultRank();

    Set<String> getPermissions();

    default boolean hasPermission(String permission) {
        return getPermissions().contains(permission);
    }

    default Predicate<String> hasPermissionPredicate() {
        return getPermissions()::contains;
    }

    default void sendInformation(Logger logger) {
        logger.info("Rank: {} | Prefix: {} | Power: {} | isDefault: {} | Permissions: {}", getName(), getPrefix(), getPower(), isDefaultRank(), String.join(",", getPermissions()));
    }

}