package fr.orion.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
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