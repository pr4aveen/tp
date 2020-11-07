package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.project.TrackedItem;

/**
 * Tests that a {@code TrackedItem}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate extends ContainsKeywordPredicate {

    /**
     * Creates a predicate to check whether the {@code Description} of a {@code Project} contains a
     * certain keyword.
     *
     * @param findType enum to indicate the find type to be used for this find command.
     * @param keywords list of keywords to check for matches.
     */
    public DescriptionContainsKeywordsPredicate(FindType findType, List<String> keywords) {
        super(findType, keywords);
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        Predicate<String> predicate = keyword ->
                StringUtil.containsPartialIgnoreCase(trackedItem.getDescription().value, keyword);
        return testPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && super.equals(other)); // state check
    }
}
