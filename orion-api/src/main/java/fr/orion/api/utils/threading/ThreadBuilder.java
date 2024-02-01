package fr.orion.api.utils.threading;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class ThreadBuilder {

    public ThreadBuilder(AtomicInteger counter) {
        counter.incrementAndGet();
    }

    public ThreadFactoryBuilder builder() {
        return new ThreadFactoryBuilder();
    }

}