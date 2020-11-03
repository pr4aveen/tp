package seedu.momentum.model.project;

/**
 * Represents a completion status in Momentum.
 * Guarantees: immutable.
 */
public class CompletionStatus implements Comparable<CompletionStatus> {
    public static final CompletionStatus COMPLETED = new CompletionStatus(true);

    // toString fields
    private static final String COMPLETED_ICON = "\u2714";
    private static final String INCOMPLETE_ICON = "\u2718";

    private final boolean completionStatus;

    /**
     * Constructs a {@code CompletionStatus}.
     */
    public CompletionStatus() {
        this.completionStatus = false;
    }

    private CompletionStatus(boolean newCompletionStatus) {
        this.completionStatus = newCompletionStatus;
    }

    /**
     * Returns true if the status is completed, false otherwise.
     *
     * @return isCompleted
     */
    public boolean isCompleted() {
        return this.completionStatus;
    }

    /**
     * Returns the reversed completion status.
     * If the completion status was true, return a completion status with false..
     *
     * @return the reverse completion status
     */
    public CompletionStatus reverse() {
        return new CompletionStatus(!this.completionStatus);
    }

    @Override
    public String toString() {
        return this.completionStatus ? COMPLETED_ICON : INCOMPLETE_ICON;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatus // instanceof handles nulls
                && this.completionStatus == ((CompletionStatus) other).completionStatus); // state check
    }

    @Override
    public int compareTo(CompletionStatus other) {
        return Boolean.compare(this.completionStatus, other.completionStatus);
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.completionStatus);
    }

}
