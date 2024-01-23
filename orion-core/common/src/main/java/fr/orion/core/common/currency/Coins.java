package fr.orion.core.common.currency;

import fr.orion.api.economy.currency.IntegerCurrency;

public class Coins extends IntegerCurrency {

    public Coins(int amount) {
        super("Coins", '⛃', amount);
    }

    public Coins() {
        this(0);
    }

}
