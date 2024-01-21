package fr.orion.lobby.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LobbyPlayer {

    private final UUID uuid;

}