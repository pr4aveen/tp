package seedu.momentum.logic.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.logic.statistic.StatisticTestUtil.TEST_MODEL;
import static seedu.momentum.logic.statistic.StatisticTestUtil.TEST_STATISTICS;
import static seedu.momentum.logic.statistic.StatisticTestUtil.TEST_WEEKLY_TIME_PER_PROJECT;
import static seedu.momentum.testutil.TypicalTimes.DAY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;

class StatisticManagerTest {

    @BeforeEach
    void setUp() {
        Clock.initFixed(DAY);
    }

    @Test
    void updateStatistics() {
        StatisticGenerator statisticGenerator = new StatisticManager(TEST_MODEL);
        statisticGenerator.updateStatistics();

        assertEquals(TEST_STATISTICS, statisticGenerator);
    }

    @Test
    void getWeeklyTimePerProjectStatistic() {
        StatisticGenerator statisticGenerator = new StatisticManager(TEST_MODEL);
        statisticGenerator.updateStatistics();

        assertEquals(TEST_WEEKLY_TIME_PER_PROJECT.getDisplayList(),
                statisticGenerator.getWeeklyTimePerProjectStatistic());
    }
}
