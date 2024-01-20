package fr.orion.api.currency;

public interface Currency<N extends Number> {

    String getName();

    N getAmount();

    void add(N amount);

    void remove(N amount);

    void setAmount(N amount);

    default void reset() {
        remove(getAmount());
    }

    boolean has(N amount);

}
