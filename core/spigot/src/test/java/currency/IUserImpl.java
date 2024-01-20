package currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class IUserImpl implements IUser {

    private final UUID uuid;
    private final Coins coins;

}
