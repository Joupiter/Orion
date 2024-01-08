package fr.orion.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class Utils {

    public <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> consumer, Runnable runnable) {
        if (optional.isPresent()) consumer.accept(optional.get());
        else runnable.run();
    }

    public <T> void ifEmpty(Optional<T> optional, Runnable runnable) {
        if (optional.isEmpty()) runnable.run();
    }

    public void ifTrue(boolean condition, Runnable runnable) {
        if (condition) runnable.run();
    }

    public <T> void ifTrue(T type, Predicate<T> predicate, Runnable runnable) {
        if (predicate.test(type)) runnable.run();
    }

    public void ifTrue(boolean firstCondition, Runnable firstRunnable, boolean secondCondition, Runnable secondRunnable) {
        ifTrue(firstCondition, firstRunnable);
        ifTrue(secondCondition, secondRunnable);
    }

    public <T> void ifTrue(T type, Predicate<T> firstPredicate, Runnable firstRunnable, Predicate<T> secondPredicate, Runnable secondRunnable) {
        ifTrue(firstPredicate.test(type), firstRunnable);
        ifTrue(secondPredicate.test(type), secondRunnable);
    }

    public <C, T> void ifTrue(C condition, T type, Predicate<C> firstPredicate, Consumer<T> firstConsumer, Predicate<C> secondPredicate, Consumer<T> secondConsumer) {
        ifTrue(firstPredicate.test(condition), () -> firstConsumer.accept(type));
        ifTrue(secondPredicate.test(condition), () -> secondConsumer.accept(type));
    }

    public <T> void ifTrue(T type, Predicate<T> firstPredicate, Runnable firstRunnable, Predicate<T> secondPredicate, Runnable secondRunnable, Predicate<T> thirdCondition, Runnable thirdRunnable) {
        ifTrue(type, firstPredicate, firstRunnable);
        ifTrue(type, secondPredicate, secondRunnable);
        ifTrue(type, thirdCondition, thirdRunnable);
    }

    public <T> void ifTrue(T type, boolean condition, Runnable runnable, Predicate<T> firstPredicate, Runnable firstRunnable, Predicate<T> secondPredicate, Runnable secondRunnable) {
        if (condition) runnable.run();
        else {
            ifTrue(type, firstPredicate, firstRunnable);
            ifTrue(type, secondPredicate, secondRunnable);
        }
    }

    public void ifFalse(boolean condition, Runnable runnable) {
        if (!condition) runnable.run();
    }

    public Map<Integer, String> stringListToMap(List<String> messages) {
        return messages.stream().collect(Collectors.toMap(messages::indexOf, Function.identity()));
    }

    @Getter
    @AllArgsConstructor
    public class BooleanWrapper {

        private final boolean condition;

        public static BooleanWrapper of(boolean condition) {
            return new BooleanWrapper(condition);
        }

        public BooleanWrapper ifTrue(Runnable runnable) {
            if (condition) runnable.run();
            return this;
        }

        public void ifFalse(Runnable runnable) {
            if (!condition) runnable.run();
        }

    }

    @Getter
    @AllArgsConstructor
    public class Value<T> {

        private final T value;

        public static <T> Value<T> of(T value) {
            return new Value<>(value);
        }

        public Value<T> consume(Consumer<T> consumer) {
            consumer.accept(getValue());
            return this;
        }

    }

    @Getter
    @AllArgsConstructor
    public class OptionalValue<T> {

        private final T value;

        public static <T> OptionalValue<T> of(T optional) {
            return new OptionalValue<>(optional);
        }

        public OptionalValue<T> consume(Consumer<T> consumer) {
            Optional.ofNullable(getValue()).ifPresent(consumer);
            return this;
        }

    }

}