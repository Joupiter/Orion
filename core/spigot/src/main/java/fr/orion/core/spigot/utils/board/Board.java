package fr.orion.core.spigot.utils.board;

import fr.orion.api.OrionApi;
import fr.orion.api.user.User;
import fr.orion.api.utils.threading.MultiThreading;
import fr.orion.core.spigot.utils.SpigotUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

@Getter
@Setter
public abstract class Board<P extends JavaPlugin> {

    private final P plugin;

    private Supplier<String> title;
    private Map<Integer, String> lines;

    private final ConcurrentMap<UUID, FastBoard> boards;

    public Board(P plugin, Supplier<String> title) {
        this.plugin = plugin;
        this.title = title;
        this.lines = new HashMap<>();
        this.boards = new ConcurrentHashMap<>();
        this.updateBoards();
    }

    public abstract List<String> getLines(User user);

    public Optional<String> getLine(int line) {
        return Optional.ofNullable(getLines().get(line));
    }

    public Optional<FastBoard> getPlayer(UUID uuid) {
        return Optional.ofNullable(getBoards().get(uuid));
    }

    public void setLines(String... lines) {
        setLines(Arrays.asList(lines));
    }

    public void setLines(List<String> lines) {
        setLines(SpigotUtils.coloredStringListToMap(lines));
    }

    public void setLines(Map<Integer, String> lines) {
        this.lines = lines;
    }

    public void updateTitle(Supplier<String> title) {
        setTitle(title);
        getBoards().values().forEach(fastBoard -> fastBoard.updateTitle(title.get()));
    }

    public void updateLine(int index, String line) {
        getLines().compute(index, (integer, s) -> SpigotUtils.colorize(line));
    }

    public void updateLines(User user) {
        getPlayer(user.getUuid()).ifPresent(board -> board.updateLines(SpigotUtils.colorize(getLines(user))));
    }

    public void addLine(int index, String line) {
        getLines().put(index, SpigotUtils.colorize(line));
    }

    public void addLine(String line) {
        getLines().values().add(SpigotUtils.colorize(line));
    }

    private void updateBoards() {
        MultiThreading.schedule(this::updateBoard, 1, 1, TimeUnit.SECONDS);
    }

    private void updateBoard() {
        getBoards().keySet().forEach(this::updateBoard);
    }

    private void updateBoard(UUID uuid) {
        MultiThreading.runCached(() -> OrionApi.getProvider().getUserRepository().getUser(uuid).subscribe(this::updateLines));
    }

    public void addViewer(Player player) {
        getBoards().computeIfAbsent(player.getUniqueId(), board -> new FastBoard(player)).updateTitle(getTitle().get());
    }

    public void removeViewer(Player player) {
        getPlayer(player.getUniqueId()).ifPresent(this::removeBoard);
    }

    public void removeBoard(FastBoard board) {
        getBoards().remove(board.getPlayer().getUniqueId());
        board.delete();
    }

}