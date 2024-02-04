package fr.orion.game.engine.phase;

import fr.orion.game.engine.Game;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Getter
@Setter
public class GamePhaseManager<G extends Game<?, ?, ?>> {

    private final G game;

    private final List<AbstractGamePhase<?>> phases;
    private AbstractGamePhase<?> currentPhase;

    public GamePhaseManager(G game) {
        this.game = game;
        this.phases = new ArrayList<>();
    }

    public final void addPhases(AbstractGamePhase<?>... phases) {
        Arrays.asList(phases).forEach(getPhases()::add);
    }

    private void setPhase(AbstractGamePhase<?> phase) {
        setCurrentPhase(phase);
        phase.startPhase();
    }

    public void tryAdvance(AbstractGamePhase<?> previousPhase) {
        getNextPhase(previousPhase).filter(equalsToCurrentPhase().negate()).ifPresentOrElse(this::setPhase, this::unregisterPhases);
    }

    public void tryRetreat(AbstractGamePhase<?> phase) {
        getPreviousPhase(phase).filter(equalsToCurrentPhase().negate()).ifPresentOrElse(this::setPhase, () -> {
            phase.unregister();
            phase.startPhase();
        });
    }

    public void start() {
        Mono.just(getPhases().get(0)).subscribe(this::setPhase);
    }

    public void unregisterPhases() {
        getPhases().forEach(AbstractGamePhase::unregister);
        getPhases().clear();
        setCurrentPhase(null);
    }

    public <T extends AbstractGamePhase<?>> void checkGamePhase(Class<T> phaseClass, Consumer<T> consumer) {
        Optional.ofNullable(getCurrentPhase())
                .filter(phaseClass::isInstance)
                .map(phaseClass::cast)
                .ifPresent(consumer);
    }

    public Optional<AbstractGamePhase<?>> getNextPhase(AbstractGamePhase<?> phase) {
        return Optional.ofNullable(getPhases().get(getPhases().indexOf(phase) + 1));
    }

    public Optional<AbstractGamePhase<?>> getPreviousPhase(AbstractGamePhase<?> phase) {
        return Optional.ofNullable(getPhases().get(getPhases().indexOf(phase) - 1));
    }

    private Predicate<AbstractGamePhase<?>> equalsToCurrentPhase() {
        return getCurrentPhase()::equals;
    }

}