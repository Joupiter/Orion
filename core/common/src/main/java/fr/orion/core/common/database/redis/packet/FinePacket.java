package fr.orion.core.common.database.redis.packet;

import fr.orion.api.database.redis.RedisPacket;
import fr.orion.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FinePacket extends RedisPacket {

    private final User user;

}