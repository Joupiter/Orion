package fr.orion.api.utils.request;

import fr.orion.api.utils.Utils;
import fr.orion.api.utils.threading.MultiThreading;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@Getter
public abstract class RequestManager<T extends Request> {

    private final long delay;
    private final TimeUnit timeUnit;

    private final ConcurrentMap<UUID, T> requests;

    public RequestManager(long delay, TimeUnit timeUnit) {
        this.delay = delay;
        this.timeUnit = timeUnit;
        this.requests = new ConcurrentHashMap<>();
    }

    protected abstract void onRequestExpire(T request);

    public Optional<T> getRequest(UUID id) {
        return Optional.ofNullable(getRequests().get(id));
    }

    public Optional<T> getRequest(UUID sender, UUID target) {
        return getOutgoingRequests(sender).stream().filter(request -> request.getTarget().equals(target)).findFirst();
    }

    public Optional<T> getRequest(Player sender, Player target) {
        return getRequest(sender.getUniqueId(), target.getUniqueId());
    }

    public void addRequest(T request, long delay, TimeUnit timeUnit) {
        getRequests().put(request.getId(), request);
        scheduleRequest(request, delay, timeUnit);
    }

    public void addRequest(T request) {
        addRequest(request, getDelay(), getTimeUnit());
    }

    public final void addRequests(Collection<T> requests) {
        addRequests(requests, getDelay(), getTimeUnit());
    }

    public final void addRequests(Collection<T> requests, long delay, TimeUnit timeUnit) {
        requests.forEach(request -> addRequest(request, delay, timeUnit));
    }

    private void scheduleRequest(Request request, long delay, TimeUnit timeUnit) {
        MultiThreading.singleSchedule(() -> Utils.Value.of(getRequests().get(request.getId())).consume(this::onRequestExpire).consume(this::removeRequest), delay, timeUnit);
    }

    private void scheduleRequest(Request request) {
        scheduleRequest(request, getDelay(), getTimeUnit());
    }

    public void removeRequest(UUID sender, UUID target) {
        getRequest(sender, target).map(Request::getId).ifPresent(getRequests()::remove);
    }

    public void removeRequest(Request request) {
        getRequests().remove(request.getId());
    }

    public void clearRequests() {
        getRequests().clear();
    }

    public List<T> getAllRequests(UUID uuid) {
        return getRequests(request -> request.getTarget().equals(uuid) || request.getSender().equals(uuid));
    }

    public List<T> getRequests(Predicate<T> predicate) {
        return getRequests().values().stream()
                .filter(predicate)
                .toList();
    }

    public List<T> getIncomingRequests(UUID uuid) {
        return getRequests(request -> request.getTarget().equals(uuid));
    }

    public List<T> getOutgoingRequests(UUID uuid) {
        return getRequests(request -> request.getSender().equals(uuid));
    }

    public boolean haveRequests(UUID uuid) {
        return !getAllRequests(uuid).isEmpty();
    }

    public boolean haveIncomingRequests(UUID uuid) {
        return !getIncomingRequests(uuid).isEmpty();
    }

    public boolean haveOutgoingRequests(UUID uuid) {
        return !getOutgoingRequests(uuid).isEmpty();
    }

    public int getIncomingRequestsCount(UUID uuid) {
        return getIncomingRequests(uuid).size();
    }

    public int getOutgoingRequestsCount(UUID uuid) {
        return getIncomingRequests(uuid).size();
    }

    public int getRequestsCount(UUID uuid) {
        return getAllRequests(uuid).size();
    }

    public int getTotalRequests() {
        return getRequests().size();
    }

}