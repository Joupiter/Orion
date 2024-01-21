package fr.orion.api.economy.currency;

import fr.orion.api.economy.Currency;

public abstract class IntegerCurrency extends Currency<Integer> {

    public IntegerCurrency(String name, char symbol, Integer amount) {
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