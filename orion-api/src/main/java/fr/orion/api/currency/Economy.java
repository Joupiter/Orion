package fr.orion.api.currency;

public interface Economy<N extends Number> {

    String getName();

    char getSymbol();

    N getAmount();

    void add(N amount);

    void remove(N amount);

    void setAmount(N amount);

    default void reset() {
        remove(getAmount());
    }

    boolean has(N amount);

}