package fr.orion.core.common.user;

import fr.orion.api.division.Division;
import fr.orion.api.division.DivisionTier;
import fr.orion.api.user.Ranking;
import fr.orion.api.utils.json.serializer.GsonImplementation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@GsonImplementation(of = Ranking.class)
public class AccountRanking implements Ranking {

    private Division division;
    private DivisionTier tier;

    private int elo;
    private double mmr;

    public AccountRanking() {
        this.division = Division.UNRANKED;
        this.tier = null;
        this.elo = 0;
        this.mmr = 0;
    }

}