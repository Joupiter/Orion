package currency;

import fr.orion.api.currency.money.IntegerMoney;
import lombok.Getter;

@Getter
public class Coins extends IntegerMoney {

    public Coins() {
        super("Coins", 0);
    }

}
