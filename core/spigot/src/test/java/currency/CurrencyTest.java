package currency;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    @Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        IUser user = new IUserImpl(uuid, new Coins());

        assertEquals(user.getCoins().getAmount(), 0);

        user.getCoins().add(200);
        assertEquals(user.getCoins().getAmount(), 200);

        user.getCoins().remove(300);
        assertEquals(user.getCoins().getAmount(), 0);

        user.getCoins().setAmount(0);
        assertTrue(user.getCoins().has(0));

        user.getCoins().setAmount(200);
        assertFalse(user.getCoins().has(300));

        user.getCoins().reset();
        assertEquals(user.getCoins().getAmount(), 0);

        System.out.println(user);
    }

}
