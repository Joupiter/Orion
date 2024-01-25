package fr.orion.api.economy.currency;

import fr.orion.api.economy.Currency;
import fr.orion.api.economy.Economy;
import fr.orion.api.utils.json.serializer.GsonImplementation;

@GsonImplementation(of = Economy.class)
public class IntegerCurrency extends Currency<Integer> {

    public IntegerCurrency(String name, String symbol, Integer amount) {
        super(name, symbol, amount);
    }

    @Override
    public void add(Integer amount) {
        setAmount(Math.max(getAmount() + amount, 0));
    }

    @Override
    public void remove(Integer amount) {
        setAmount(Math.max(getAmount() - amount, 0));
    }

    @Override
    public boolean has(Integer amount) {
        return getAmount() >= amount;
    }

}