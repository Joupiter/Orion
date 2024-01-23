package fr.orion.api.division;

import java.util.Set;

public enum DivisionTier {

    I,
    II,
    III;

    public static Set<DivisionTier> getTiers() {
        return Set.of(values());
    }

}