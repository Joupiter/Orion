package fr.orion.api.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public abstract class Money<N extends Number> implements Currency<N> {

    private final String name;

    private N amount;

}