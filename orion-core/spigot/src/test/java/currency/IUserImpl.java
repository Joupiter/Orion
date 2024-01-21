package currency;

import fr.orion.api.economy.currency.IntegerCurrency;

import java.util.UUID;

public class IUserImpl implements IUser {

    private final UUID uuid;
    private final Coins coins;

    public IUserImpl(UUID uuid, Coins coins) {
        this.uuid = uuid;
        this.coins = coins;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public IntegerCurrency getCoins() {
        return coins;
    }

}
