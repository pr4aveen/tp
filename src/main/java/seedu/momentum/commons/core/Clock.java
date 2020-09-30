package seedu.momentum.commons.core;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import seedu.momentum.model.timer.Time;

/**
 * Represents a clock used by the application.
 */
public class Clock {
    private static ClockState clockState = ClockState.NORMAL;
    private static Time currentTime;

    /**
     * Gets the current time according to the state of the clock.
     *
     * @return System time if normal, A fixed time if fixed, and The set time if manual.
     */
    public static Time now() {
        if (clockState == ClockState.NORMAL) {
            return new Time(LocalDateTime.now());
        }

        return currentTime;
    }

    /**
     * Starts the clock with a fixed time.
     *
     * @param fixedTime The time to start the clock at.
     */
    public static void initFixed(Time fixedTime) {
        currentTime = fixedTime;
        clockState = ClockState.FIXED;
    }

    /**
     * Starts a manually adjustable clock.
     *
     * @param startTime The starting time of the clock.
     */
    public static void initManual(Time startTime) {
        currentTime = startTime;
        clockState = ClockState.MANUAL;
    }

    /**
     * Advances the clock's time.
     *
     * @param amount Amount to advance by.
     * @param units Units of time to advance by.
     */
    public static void advance(long amount, ChronoUnit units) {
        assert(clockState == ClockState.MANUAL);
        currentTime = currentTime.plus(amount, units);
    }

    /** Reverses the clock's time.
     *
     * @param amount Amount to advance by.
     * @param units Units of time to advance by.
     */
    public static void reverse(long amount, ChronoUnit units) {
        assert(clockState == ClockState.MANUAL);
        currentTime = currentTime.minus(amount, units);
    }

    /**
     * Resets the Clock to a normal state.
     */
    public static void reset() {
        currentTime = new Time(LocalDateTime.now());
        clockState = ClockState.NORMAL;
    }

    /**
     * Represents the state of the clock.
     * Fixed: The clock always gives the same time.
     * Manual: The clock's time can be manually adjusted.
     * Normal: The clock's time follows system time.
     */
    public enum ClockState {
        FIXED,
        MANUAL,
        NORMAL
    }
}
