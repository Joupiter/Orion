package fr.orion.api.utils.request;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@ToString
public abstract class Request {

    private final UUID id, sender, target;

    public Request(UUID sender, UUID target) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.target = target;
    }

    public Optional<Player> getSenderPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(getSender()));
    }

    public Optional<Player> getTargetPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(getTarget()));
    }

    public List<Player> getPlayers() {
        return List.of(Bukkit.getPlayer(getSender()), Bukkit.getPlayer(getTarget()));
    }

}