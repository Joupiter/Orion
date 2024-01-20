package currency;

import fr.orion.api.currency.money.IntegerMoney;

import java.util.UUID;

public interface IUser {

    UUID getUuid();

    IntegerMoney getCoins();

}
