package seedu.momentum.logic.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.logic.statistic.StatisticTestUtil.TEST_MODEL;
import static seedu.momentum.logic.statistic.StatisticTestUtil.TEST_WEEKLY_TIME_PER_PROJECT;
import static seedu.momentum.testutil.Assert.assertThrows;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

class PeriodicTotalTimePerProjectStatisticTest {
    @Test
    public void constructor_nullPeriod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new PeriodicTotalTimePerProjectStatistic(null, ChronoUnit.MINUTES));
    }

    @Test
    public void constructor_nullUnits_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new PeriodicTotalTimePerProjectStatistic(ChronoUnit.WEEKS, null));
    }

    @Test
    public void calculate_typicalProjects_correctData() {
        PeriodicTotalTimePerProjectStatistic stat =
                new PeriodicTotalTimePerProjectStatistic(ChronoUnit.WEEKS, ChronoUnit.MINUTES);
        stat.calculate(TEST_MODEL);

        assertEquals(TEST_WEEKLY_TIME_PER_PROJECT, stat);
    }

}
