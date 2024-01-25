package fr.orion.api.economy.currency;

import fr.orion.api.economy.Currency;
import fr.orion.api.economy.Economy;
import fr.orion.api.utils.json.serializer.GsonImplementation;

@GsonImplementation(of = Economy.class)
public class DoubleCurrency extends Currency<Double> {

    public DoubleCurrency(String name, String symbol, Double amount) {
        super(name, symbol, amount);
    }

    @Override
    public void add(Double amount) {
        setAmount(Math.max(getAmount() + amount, 0));
    }

    @Override
    public void remove(Double amount) {
        setAmount(Math.max(getAmount() - amount, 0));
    }

    @Override
    public boolean has(Double amount) {
        return getAmount() >= amount;
    }

}