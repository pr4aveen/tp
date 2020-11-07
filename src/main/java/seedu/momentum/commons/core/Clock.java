//@@author boundtotheearth

package seedu.momentum.commons.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Represents a clock used by the application.
 */
public class Clock {
    private static ClockState clockState = ClockState.NORMAL;
    private static DateTimeWrapper currentDateTime;

    /**
     * Gets the current time according to the state of the clock.
     *
     * @return System time if normal, a fixed time if fixed, and the set time if manual.
     */
    public static DateTimeWrapper now() {
        if (clockState == ClockState.NORMAL) {
            return new DateTimeWrapper(LocalDateTime.now());
        }

        return currentDateTime;
    }

    /**
     * Starts the clock with a fixed time.
     *
     * @param fixedDateTime The time to start the clock at.
     */
    public static void initFixed(DateTimeWrapper fixedDateTime) {
        currentDateTime = fixedDateTime;
        clockState = ClockState.FIXED;
    }

    /**
     * Starts a manually adjustable clock.
     *
     * @param startDateTime The starting time of the clock.
     */
    public static void initManual(DateTimeWrapper startDateTime) {
        currentDateTime = startDateTime;
        clockState = ClockState.MANUAL;
    }

    /**
     * Advances the clock's time.
     *
     * @param amount Amount to advance by.
     * @param units  Units of time to advance by.
     */
    public static void advance(long amount, ChronoUnit units) {
        assert (clockState == ClockState.MANUAL);
        currentDateTime = currentDateTime.plus(amount, units);
    }

    /**
     * Reverses the clock's time.
     *
     * @param amount Amount to advance by.
     * @param units  Units of time to advance by.
     */
    public static void reverse(long amount, ChronoUnit units) {
        assert (clockState == ClockState.MANUAL);
        currentDateTime = currentDateTime.minus(amount, units);
    }

    /**
     * Resets the Clock to a normal state.
     */
    public static void reset() {
        currentDateTime = new DateTimeWrapper(LocalDateTime.now());
        clockState = ClockState.NORMAL;
    }

    /**
     * Represents the state of the clock.
     * Fixed: The clock always gives the same time.
     * Manual: The clock's time can be manually adjusted.
     * Normal: The clock's time follows system time.
     */
    private enum ClockState {
        FIXED,
        MANUAL,
        NORMAL
    }
}
