import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Consumer;

public class ReactorTest {

    @Test
    public void test() {
        Flux<String> flux = Flux.just("a1", "a3", "a2");

        Mono<String> mono = Mono.just("Hello world!");
        Mono<String> emptyMono = Mono.empty();

        flux.log()
                .onErrorContinue((throwable, s) -> throwable.printStackTrace())
                .doOnNext(s -> System.out.println("next: " + s))
                .subscribe(s -> System.out.println("complete: " + s));

        Mono.just(Optional.empty())
                .map(Optional::orElseThrow)
                .subscribe(System.out::println, throwable -> System.out.println("error :)"));

        ifPresentOrElse(emptyMono, System.out::println, () -> System.out.println("error :)"));

        System.out.println(emptyMono.hasElement().blockOptional().orElse(false));
        System.out.println(mono.hasElement().blockOptional().orElse(false));
    }

    public <T> void ifPresentOrElse(Mono<T> mono, Consumer<T> consumer, Runnable runnable) {
        mono.switchIfEmpty(Mono.defer(() -> {
            runnable.run();
            return Mono.empty();
        })).subscribe(consumer);
    }

}