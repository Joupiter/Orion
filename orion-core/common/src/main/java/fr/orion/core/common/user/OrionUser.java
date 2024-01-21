package fr.orion.core.common.user;

import fr.orion.api.user.User;
import fr.orion.core.common.currency.Coins;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrionUser implements User {

    private final UUID uuid;
    private final Coins coins;

    public OrionUser(UUID uuid) {
        this(uuid, new Coins());
    }

}
