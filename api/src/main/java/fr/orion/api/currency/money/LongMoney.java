package fr.orion.api.currency.money;

import fr.orion.api.currency.Money;

public abstract class LongMoney extends Money<Long> {

    public LongMoney(String name, Long amount) {
        super(name, amount);
    }

    @Override
    public void add(Long amount) {
        setAmount(Math.max(getAmount() + amount, 0));
    }

    @Override
    public void remove(Long amount) {
        setAmount(Math.max(getAmount() - amount, 0));
    }

    @Override
    public boolean has(Long amount) {
        return getAmount() >= amount;
    }

}