package fr.orion.core.common.database.redis.packet;

import fr.orion.api.database.redis.RedisPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExamplePacket extends RedisPacket {

    private final String field;

}
