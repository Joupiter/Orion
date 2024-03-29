package fr.orion.api.utils.math;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

@Getter
@AllArgsConstructor
public enum Operator {

    ADD ('+', Double::sum),
    SUBTRACT ('-', (a, b) -> a - b),
    MULTIPLY ('*', (a, b) -> a * b),
    DIVIDE ('/', (a, b) -> a / b),
    MODULO ('%', (a, b) -> a % b);

    private final char character;
    private final DoubleBinaryOperator operation;

    public double apply(double a, double b) {
        return getOperation().applyAsDouble(a, b);
    }

    public double apply(double... numbers) {
        return Arrays.stream(numbers).reduce(this::apply).orElseThrow();
    }

}