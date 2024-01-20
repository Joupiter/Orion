package fr.orion.api.currency.money;

import fr.orion.api.currency.Money;

public abstract class DoubleMoney extends Money<Double> {

    public DoubleMoney(String name, Double amount) {
        super(name, amount);
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