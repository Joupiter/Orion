package fr.orion.lobby.common;

import fr.orion.api.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@Setter
public class SomeTest {

    private final User user;
    private Supplier<String> supplier;

    public SomeTest(User user) {
        this.user = user;
        this.supplier = () -> "Coins: " + user.getCoins();
    }

}
