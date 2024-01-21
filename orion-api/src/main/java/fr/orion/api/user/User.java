package fr.orion.api.user;

import fr.orion.api.economy.currency.IntegerCurrency;

import java.util.UUID;

public interface User {

    UUID getUuid();

    IntegerCurrency getCoins();

}