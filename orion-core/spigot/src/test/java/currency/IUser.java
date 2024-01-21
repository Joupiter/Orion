package currency;

import fr.orion.api.economy.currency.IntegerCurrency;

import java.util.UUID;

public interface IUser {

    UUID getUuid();

    IntegerCurrency getCoins();

}
