package testing;

import fr.orion.api.division.Division;
import fr.orion.api.division.DivisionTier;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class RankingTest {

    static Logger logger = LoggerFactory.getLogger(RankingTest.class);

    @Test
    public void test() {
        UserRanking userRanking = new UserRanking(Division.EMERALD, DivisionTier.II, 52, 156);
        logger.info(userRanking.toString());
        logger.info(Division.getDivisionsSorted().stream().map(Division::getName).collect(Collectors.joining(",")));

        logger.info("Estimated MMR: {}", userRanking.getCombinedMmr());
        logger.info("Estimated Division: {}", userRanking.getEstimatedDivision());
    }

}