package fr.orion.api.utils;

import io.github.classgraph.ClassInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

    public <T> void ifPresentOrElse(Mono<T> mono, Consumer<T> consumer, Runnable runnable) {
        mono.switchIfEmpty(Mono.defer(() -> {
            runnable.run();
            return Mono.empty();
        })).subscribe(consumer);
    }

    public <T> void ifPresentOrElse(Flux<T> flux, Consumer<T> consumer, Runnable runnable) {
        flux.switchIfEmpty(Flux.defer(() -> {
            runnable.run();
            return Flux.empty();
        })).subscribe(consumer);
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

    public String randomString(int length) {
        return ThreadLocalRandom.current()
                .ints(48, 123)
                .filter(num -> (num < 58 || num > 64) && (num < 91 || num > 96))
                .limit(length)
                .mapToObj(Character::toString)
                .collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
    }

    @SafeVarargs
    public static <T> List<T> mergeList(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).toList();
    }

    public Map<Integer, String> stringListToMap(List<String> messages) {
        return messages.stream().collect(Collectors.toMap(messages::indexOf, Function.identity()));
    }

    public Class<?> getClassFromClassInfo(ClassInfo classInfo) {
        return getClassFromName(classInfo.getName());
    }

    public Class<?> getClassFromName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
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

        public Value<T> optionalConsume(Consumer<T> consumer) {
            Optional.ofNullable(getValue()).ifPresent(consumer);
            return this;
        }

    }

}