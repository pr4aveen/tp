//@@author kkangs0226
package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;

/**
 * Represent a description in Momentum.
 * Guarantees: immutable.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description will be removed if blank";
    public static final Description EMPTY_DESCRIPTION = new Description("");

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description The description.
     */
    public Description(String description) {
        requireNonNull(description);
        value = description;
    }

    public boolean isEmpty() {
        return value.isBlank();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
