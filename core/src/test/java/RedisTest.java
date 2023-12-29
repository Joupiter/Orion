import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.orion.api.OrionApi;
import fr.orion.api.user.User;
import fr.orion.core.OrionImpl;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisTest {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final UUID uuid = UUID.randomUUID();
    private final User user = new User(uuid, 1555);

    @Test
    public void test() {
        OrionApi.setProvider(new OrionImpl());
        OrionApi.getProvider().getDatabaseLoader().connect();

        System.out.println("----------------------");
        OrionApi.getProvider().getDatabaseLoader().getRedisDatabase().getConnection().reactive()
                .set(getUserRedisKey(uuid), gson.toJson(user))
                .subscribe(System.out::println);
        System.out.println("----------------------");

        OrionApi.getProvider().getDatabaseLoader().getRedisDatabase().getConnection().reactive().get(getUserRedisKey(uuid))
                .subscribe(this::sameUser);

        OrionApi.getProvider().getDatabaseLoader().disconnect();
    }

    public String getUserRedisKey(UUID uuid) {
        return "users:" + uuid.toString();
    }

    public void sameUser(String json) {
        assertEquals(user, gson.fromJson(json, User.class));
    }

}
