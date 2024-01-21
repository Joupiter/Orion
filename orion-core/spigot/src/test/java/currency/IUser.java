package currency;

import fr.orion.api.currency.money.IntegerCurrency;

import java.util.UUID;

public interface IUser {

    UUID getUuid();

    IntegerCurrency getCoins();

}
