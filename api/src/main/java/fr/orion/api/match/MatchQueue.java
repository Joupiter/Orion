package fr.orion.api.match;

import lombok.Getter;

import java.util.Deque;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Getter
public class MatchQueue {

    private final String name;
    private final Deque<UUID> queue;

    private MatchQueue(String name) {
        this.name = name;
        this.queue = new ConcurrentLinkedDeque<>();
    }

}
