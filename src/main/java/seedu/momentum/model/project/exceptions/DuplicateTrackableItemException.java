package seedu.momentum.model.project.exceptions;

/**
 * Signals that the operation will result in duplicate trackable items (Trackable items are considered
 * duplicates if they have the same identity).
 */
public class DuplicateTrackableItemException extends RuntimeException {
    public DuplicateTrackableItemException() {
        super("Operation would result in duplicate trackable items");
    }
}
