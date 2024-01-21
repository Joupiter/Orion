package currency;

import fr.orion.api.economy.currency.IntegerCurrency;
import lombok.Getter;

@Getter
public class Coins extends IntegerCurrency {

    public Coins() {
        super("Coins", '⛃', 0);
    }

}