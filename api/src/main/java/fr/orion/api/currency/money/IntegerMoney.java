package fr.orion.api.currency.money;

import fr.orion.api.currency.Money;

public abstract class IntegerMoney extends Money<Integer> {

    public IntegerMoney(String name, Integer amount) {
        super(name, amount);
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