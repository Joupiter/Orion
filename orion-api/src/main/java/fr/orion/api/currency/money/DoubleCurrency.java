package fr.orion.api.currency.money;

import fr.orion.api.currency.Currency;

public abstract class DoubleCurrency extends Currency<Double> {

    public DoubleCurrency(String name, char symbol, Double amount) {
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