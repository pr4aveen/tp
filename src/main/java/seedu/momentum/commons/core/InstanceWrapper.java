//@@author claracheong4

package seedu.momentum.commons.core;

/**
 * Represents an instance in time in the project book.
 */
public interface InstanceWrapper<T> {
    /**
     * Returns an instance in time with type T.
     *
     * @return The instance in time.
     */
    T get();

    /**
     * Returns a formatted string representation of instance in time.
     *
     * @return The formatted instance in time.
     */
    String getFormatted();
}
