package fr.orion.api.user;

import fr.orion.api.division.Division;
import fr.orion.api.division.DivisionTier;
import fr.orion.api.utils.json.serializer.ApiSerializable;

public interface Ranking extends ApiSerializable {

    Division getDivision();
    DivisionTier getTier();

    int getElo();
    double getMmr(); /* DIVISION_BASE_MMR + USER_MMR = MMR
                           1200 + 563 = 1763 (Affect elo point gained)
                           ex: 1200 = GOLD III, 1700 (PLAT I) user -> user estimated rank is PLAT I
                           when lost: -17 elo point and -0.9642654566 mmr (PLAT_I_MMR / USER_MMR)
                           when win: +45 elo point and +1.40833333333 mmr (GAME_AVERAGE_MMR / GAME_AVERAGE_DIVISION_MMR)
                           GAME_AVERAGE_MMR = 1690
                           GAME_AVERAGE_DIVISION_MMR = 1200 (GOLD III)
                           (1763 + 1710 + 1690 + 1597) = 6760 / 4 = 1690
                           (USERS_MMR) = SUM / NUMBER_OF_USERS = AVERAGE
                        */

    default double getCombinedMmr() {
        return getDivision().getBaseMmr() + getMmr();
    }

    default Division getEstimatedDivision() {
        return Division.estimateDivision(getMmr());
    }

    void setDivision(Division division);

    void setTier(DivisionTier tier);

    void setElo(int elo);

    void setMmr(double mmr);

}