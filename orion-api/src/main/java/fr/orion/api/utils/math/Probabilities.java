package fr.orion.api.utils.math;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Probabilities<T> {

    private final Random random;
    private final ConcurrentMap<T, Float> items;

    public Probabilities() {
        this.random = ThreadLocalRandom.current();
        this.items = new ConcurrentHashMap<>();
    }

    public Probabilities<T> add(T item, float probability) {
        getItems().put(item, probability);
        return this;
    }

    public Probabilities<T> add(Map<T, Float> map) {
        getItems().putAll(map);
        return this;
    }

    public void remove(T item) {
        getItems().remove(item);
    }

    public boolean contains(T item) {
        return getItems().containsKey(item);
    }

    public float getProbability(T item) {
        return getItems().getOrDefault(item, 0f);
    }

    public float getTotal() {
        return (float) getItems().values().stream().mapToDouble(Float::doubleValue).sum();
    }

    public Optional<T> randomize() {
        final float result = getRandom().nextFloat() * getTotal();
        final AtomicReference<Float> current = new AtomicReference<>(0f);

        return getItems().entrySet().stream()
                .filter(predicate(result, current))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public List<T> randomize(int number) {
        return IntStream.range(0, number)
                .mapToObj(i -> randomize().orElseThrow())
                .collect(Collectors.toList());
    }

    private Predicate<Map.Entry<T, Float>> predicate(float result, AtomicReference<Float> current) {
        return entry -> result >= current.get() && result < (current.updateAndGet(v -> v + entry.getValue()));
    }

}