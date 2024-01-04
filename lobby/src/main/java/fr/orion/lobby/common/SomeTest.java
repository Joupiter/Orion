package fr.orion.lobby.common;

import fr.orion.api.user.User;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class SomeTest {

    private final User user;
    private final Supplier<String> supplier;

    public SomeTest(User user) {
        this.user = user;
        this.supplier = () -> "Coins: " + user.getCoins();
    }

}
