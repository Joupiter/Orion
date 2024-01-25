package fr.orion.api.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Currency<N extends Number> implements Economy<N> {

    private String name;
    private String symbol;

    private N amount;

}