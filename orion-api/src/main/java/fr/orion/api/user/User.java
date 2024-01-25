package fr.orion.api.user;

import fr.orion.api.economy.currency.IntegerCurrency;
import fr.orion.api.utils.json.serializer.ApiSerializable;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface User extends ApiSerializable {

    UUID getUuid();

    IntegerCurrency getCoins();

    Ranking getRanking();

    default Session getSession() {
        throw new NoSuchElementException("Need to be implemented");
    }

}