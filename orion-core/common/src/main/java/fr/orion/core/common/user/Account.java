package fr.orion.core.common.user;

import fr.orion.api.economy.CurrencyFactory;
import fr.orion.api.economy.currency.IntegerCurrency;
import fr.orion.api.user.User;
import fr.orion.api.utils.json.serializer.GsonImplementation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@GsonImplementation(of = User.class)
public class Account implements User {

    private final UUID uuid;

    private final IntegerCurrency coins;
    private final AccountRanking ranking;

    public Account(UUID uuid) {
        this(uuid, CurrencyFactory.newIntegerCurrency("Coins", "⛃"), new AccountRanking());
    }

}