package fr.orion.api.economy;

import fr.orion.api.utils.json.serializer.ApiSerializable;

public interface Economy<N extends Number> extends ApiSerializable {

    String getName();

    String getSymbol();

    N getAmount();

    void add(N amount);

    void remove(N amount);

    void setAmount(N amount);

    default void reset() {
        remove(getAmount());
    }

    boolean has(N amount);

}