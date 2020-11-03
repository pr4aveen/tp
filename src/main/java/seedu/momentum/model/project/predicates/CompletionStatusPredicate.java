package seedu.momentum.model.project.predicates;

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
    private final FindType findType;

    /**
     * Creates a predicate to check whether the {@code CompletionStatus} of a {@code Project} is incomplete.
     *
     * @param findType enum to indicate the find type to be used for this find command.
     * @param keywords list of keywords to check for matches.
     */
    public CompletionStatusPredicate(FindType findType, List<String> keywords) {
        assert keywords.size() == 1;
        this.findType = findType;
        this.keywords = keywords;
    }

    /**
     * Checks if the keywords are valid.
     *
     * @param keywords The keywords to check
     * @return True if keywords are valid, false otherwise.
     */
    public static boolean isValid(List<String> keywords) {
        return keywords.size() == 1 // check that there is only 1 keyword
                && (keywords.get(0).equals(COMPLETED_KEYWORD) // check that the keyword is valid
                || keywords.get(0).equals(INCOMPLETE_KEYWORD)); // check that the keyword is valid
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        String keyword = keywords.get(0);
        boolean status = trackedItem.getCompletionStatus().isCompleted();
        switch (keyword) {
        case COMPLETED_KEYWORD:
            return status;
        case INCOMPLETE_KEYWORD:
            return !status;
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompletionStatusPredicate // instanceof handles nulls
                && keywords.equals(((CompletionStatusPredicate) other).keywords)); // state check
    }

}
