package fr.orion.api.division;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public enum Division {

    CHALLENGER ("Challenger", DivisionTier.I, 3300),
    GRANDMASTER ("Grand Master", DivisionTier.I, 2950),
    MASTER ("Master", DivisionTier.I, 2700),

    DIAMOND ("Diamond", DivisionTier.getTiers(),  2300),
    EMERALD ("Emerald", DivisionTier.getTiers(), 1800),
    PLATINUM ("Platinum", DivisionTier.getTiers(), 1600),

    GOLD ("Gold", DivisionTier.getTiers(), 1350),
    SILVER ("Silver", DivisionTier.getTiers(), 1000),
    BRONZE ("Bronze", DivisionTier.getTiers(), 0),
    UNRANKED ("Unranked", Collections.emptySet(), 0);

    private final String name;
    private final Set<DivisionTier> tier;

    private final double baseMmr;

    Division(String name, DivisionTier tier, double baseMmr) {
        this(name, Set.of(tier), baseMmr);
    }

    public static List<Division> getDivisions() {
        return Arrays.asList(values());
    }

    public static List<Division> getDivisionsSorted() {
        return getDivisions().stream()
                .sorted(Comparator.comparingDouble(Division::getBaseMmr))
                .toList();
    }

    public static Division estimateDivision(double mmr) {
        return getDivisions().stream()
                .filter(division -> mmr >= division.getBaseMmr())
                .max(Comparator.comparingDouble(Division::getBaseMmr))
                .orElse(Division.BRONZE);
    }

}