package fr.orion.api.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class Rank {

    private String name, prefix;

    private int power;
    private boolean defaultRank;

    private List<String> permissions;

    public boolean hasPermission(String permission) {
        return getPermissions().stream()
                .anyMatch(permission::equalsIgnoreCase);
    }

}
