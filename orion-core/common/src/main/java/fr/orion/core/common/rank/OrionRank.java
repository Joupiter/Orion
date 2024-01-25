package fr.orion.core.common.rank;

import fr.orion.api.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class OrionRank implements Rank {

    private String name, prefix;

    private int power;
    private boolean defaultRank;

    private Set<String> permissions;

}