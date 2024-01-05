package fr.orion.lobby.common;

import fr.orion.api.user.User;
import fr.orion.core.spigot.utils.board.Board;
import fr.orion.lobby.LobbyPlugin;

import java.util.List;

public class LobbyBoard extends Board<LobbyPlugin> {

    public LobbyBoard(LobbyPlugin plugin) {
        super(plugin, () -> "&aOrion");
    }

    @Override
    public List<String> getLines(User user) {
        return List.of(
                "",
                "&eCoins&7: &b" + user.getCoins(),
                "");
    }

}
