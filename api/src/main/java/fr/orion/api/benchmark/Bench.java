package fr.orion.api.benchmark;


public interface Bench {

    String getName();

    void test();

    default void notify(String message) {
        System.out.println("[Benchmark] (" + getName() + ") : " + message);
    }

}
