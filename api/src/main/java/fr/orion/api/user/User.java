package fr.orion.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class User {

    private final UUID uuid;

    @Setter private int coins;

}
