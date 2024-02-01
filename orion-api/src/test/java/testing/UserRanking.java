package testing;

import fr.orion.api.division.Division;
import fr.orion.api.division.DivisionTier;
import fr.orion.api.user.Ranking;

public class UserRanking implements Ranking {

    private Division division;
    private DivisionTier tier;

    private int elo;
    private double mmr;

    public UserRanking(Division division, DivisionTier tier, int elo, double mmr) {
        this.division = division;
        this.tier = tier;
        this.elo = elo;
        this.mmr = mmr;
    }

    @Override
    public Division getDivision() {
        return division;
    }

    @Override
    public DivisionTier getTier() {
        return tier;
    }

    @Override
    public int getElo() {
        return elo;
    }

    @Override
    public double getMmr() {
        return mmr;
    }

    @Override
    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public void setTier(DivisionTier tier) {
        this.tier = tier;
    }

    @Override
    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public void setMmr(double mmr) {
        this.mmr = mmr;
    }

    @Override
    public String toString() {
        return "testing.UserRanking{" +
                "division=" + division +
                ", tier=" + tier +
                ", elo=" + elo +
                ", mmr=" + mmr +
                '}';
    }

}