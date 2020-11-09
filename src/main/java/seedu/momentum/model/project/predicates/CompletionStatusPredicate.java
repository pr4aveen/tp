//@@author claracheong4
package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

/**
 * Tests that a {@code TrackedItem}'s {@code CompletionStatus} is incomplete.
 */
public class CompletionStatusPredicate extends ContainsKeywordPredicate {

    public static final String COMPLETED_KEYWORD = "completed";
    public static final String INCOMPLETE_KEYWORD = "incomplete";

    private static final Predicate<String> MATCH_COMPLETE = COMPLETED_KEYWORD::equals;

    private static final Predicate<String> MATCH_INCOMPLETE = INCOMPLETE_KEYWORD::equals;

    /**
     * Creates a predicate to check whether the {@code CompletionStatus} of a {@code Project} is incomplete.
     *
     * @param findType Enum to indicate the find type to be used for this find command.
     * @param keywords List of keywords to check for matches.
     */
    public CompletionStatusPredicate(FindType findType, List<String> keywords) {
        super(findType, keywords);
        checkArgument(isValid(keywords));
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
                && (MATCH_COMPLETE.test(keywords.get(0))
                || MATCH_INCOMPLETE.test(keywords.get(0)));
    }

    @Override
    protected boolean testPredicate(Predicate<String> predicate) {
        requireNonNull(predicate);
        String keyword = keywords.get(0);
        if (findType == FindType.NONE) {
            return predicate.negate().test(keyword);
        }
        return predicate.test(keyword);
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        boolean status = trackedItem.getCompletionStatus().isCompleted();
        return testPredicate(MATCH_COMPLETE) == status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof CompletionStatusPredicate) // instanceof handles nulls
                && super.equals(other));
    }
}
