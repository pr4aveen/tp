package seedu.momentum.model.timer.exceptions;

/**
 * Signals that the operation will result in duplicate Timers (Timers are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDurationException extends RuntimeException {
    public DuplicateDurationException() {
        super("Operation would result in duplicate timers");
    }
}
