package seedu.momentum.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class StatisticTimeframeTest {

    private static final StatisticTimeframe DAILY_TIMEFRAME =
        new StatisticTimeframe(StatisticTimeframe.Timeframe.DAILY);

    private static final StatisticTimeframe WEEKLY_TIMEFRAME =
        new StatisticTimeframe(StatisticTimeframe.Timeframe.WEEKLY);

    private static final StatisticTimeframe MONTHLY_TIMEFRAME =
        new StatisticTimeframe(StatisticTimeframe.Timeframe.MONTHLY);

    @Test
    public void constructor_invalidTimeframe_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new StatisticTimeframe("4930"));
    }

    @Test
    public void isValid() {
        // null -> throws exception
        assertThrows(NullPointerException.class, () -> StatisticTimeframe.isValid(null));

        // invalid timeframe -> return false
        assertFalse(StatisticTimeframe.isValid("yearly"));

        // valid timeframe -> return true
        assertTrue(StatisticTimeframe.isValid("weekly"));
        assertTrue(StatisticTimeframe.isValid("daily"));
        assertTrue(StatisticTimeframe.isValid("monthly"));
    }

    @Test
    public void toChronoUnit() {
        assertEquals(DAILY_TIMEFRAME.toChronoUnit(), ChronoUnit.DAYS);
        assertEquals(WEEKLY_TIMEFRAME.toChronoUnit(), ChronoUnit.WEEKS);
        assertEquals(MONTHLY_TIMEFRAME.toChronoUnit(), ChronoUnit.MONTHS);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(DAILY_TIMEFRAME.equals(DAILY_TIMEFRAME));

        // same timeframe -> returns true
        assertTrue(DAILY_TIMEFRAME.equals(new StatisticTimeframe(StatisticTimeframe.Timeframe.DAILY)));

        // different types -> returns false
        assertFalse(DAILY_TIMEFRAME.equals(3));

        // null -> return false
        assertFalse(DAILY_TIMEFRAME.equals(null));

        // different timeframe -> return false
        assertFalse(DAILY_TIMEFRAME.equals(MONTHLY_TIMEFRAME));
    }
}
