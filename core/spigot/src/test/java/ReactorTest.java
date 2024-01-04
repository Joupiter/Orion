import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ReactorTest {

    @Test
    public void test() {
        Flux<String> flux = Flux.just("a1", "a3", "a2");

        flux.log()
                .onErrorContinue((throwable, s) -> throwable.printStackTrace())
                .doOnNext(s -> System.out.println("next: " + s))
                .subscribe(s -> System.out.println("complete: " + s));

        Mono.just(Optional.empty())
                .map(Optional::orElseThrow)
                .subscribe(System.out::println, throwable -> System.out.println("error :)"));
    }

}
