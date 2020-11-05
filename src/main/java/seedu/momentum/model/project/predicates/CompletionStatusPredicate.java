package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

/**
 * Tests that a {@code TrackedItem}'s {@code CompletionStatus} is incomplete.
 */
public class CompletionStatusPredicate implements Predicate<TrackedItem> {
    public static final String COMPLETED_KEYWORD = "completed";
    public static final String INCOMPLETE_KEYWORD = "incomplete";

    private final List<String> keywords;

    /**
     * Creates a predicate to check whether the {@code CompletionStatus} of a {@code Project} is incomplete.
     *
     * @param keywords list of keywords to check for matches.
     */
    public CompletionStatusPredicate(List<String> keywords) {
        requireNonNull(keywords);
        checkArgument(isValid(keywords));
        this.keywords = keywords;
    }

    /**
     * Checks if the keywords are valid.
     *
     * @param keywords The keywords to check.
     * @return True if keywords are valid, false otherwise.
     */
    public static boolean isValid(List<String> keywords) {
        requireNonNull(keywords);
        return keywords.size() == 1 // check that there is only 1 keyword
                && (keywords.get(0).equals(COMPLETED_KEYWORD) // check that the keyword is valid
                || keywords.get(0).equals(INCOMPLETE_KEYWORD)); // check that the keyword is valid
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        String keyword = keywords.get(0);
        boolean status = trackedItem.getCompletionStatus().isCompleted();
        if (keyword.equals(COMPLETED_KEYWORD)) {
            return status;
        }
        return !status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatusPredicate // instanceof handles nulls
                && keywords.equals(((CompletionStatusPredicate) other).keywords)); // state check
    }

}
