package fr.orion.api.division;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@AllArgsConstructor
public class Division {

    private final String divisionName;

    private final Set<DivisionTier> tiers;

}
