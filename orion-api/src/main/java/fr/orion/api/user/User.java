package fr.orion.api.user;

import fr.orion.api.economy.currency.IntegerCurrency;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface User {

    UUID getUuid();

    IntegerCurrency getCoins();

    default UserRanking getRanking() {
        throw new NoSuchElementException("Need to be implemented");
    }

}