import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.orion.api.OrionApi;
import fr.orion.api.user.User;
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
        OrionApi.getProvider().getDatabaseLoader().getRedisDatabase().getReactiveCommands()
                .set(getUserRedisKey(uuid), gson.toJson(user)).log().subscribe();
        System.out.println(gson.toJson(user));
        System.out.println("----------------------");

        OrionApi.getProvider().getDatabaseLoader().getRedisDatabase().getReactiveCommands().get(getUserRedisKey(uuid))
                .map(this::convert).log().subscribe(this::sameUser);

        OrionApi.getProvider().getDatabaseLoader().disconnect();
    }

    public String getUserRedisKey(UUID uuid) {
        return "users:" + uuid.toString();
    }

    public User convert(String json) {
        return gson.fromJson(json, User.class);
    }

    public void sameUser(User fromRedis) {
       assertEquals(user.getUuid(), fromRedis.getUuid());
    }

}
