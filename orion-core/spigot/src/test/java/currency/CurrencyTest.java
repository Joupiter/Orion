package currency;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyTest {

    static Logger logger = LoggerFactory.getLogger(CurrencyTest.class);

    @Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        IUser user = new IUserImpl(uuid, new Coins());

        // ⛂⛃ ⛀⛁
        logger.info("⛂⛃ ⛀⛁");

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

        logger.info("[BANK] Account: {} has {} {}", user.getUuid(), user.getCoins().getAmount(), user.getCoins().getSymbol());
    }

}
