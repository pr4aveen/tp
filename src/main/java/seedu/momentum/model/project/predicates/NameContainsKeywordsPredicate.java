//@@author pr4aveen
package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.project.TrackedItem;

/**
 * Tests that a {@code TrackedItem}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate extends ContainsKeywordPredicate {

    /**
     * Creates a predicate to check whether the {@code Name} of a {@code TrackedItem} contains a
     * certain keyword.
     *
     * @param findType Enum to indicate the find type to be used for this find command.
     * @param keywords List of keywords to check for matches.
     */
    public NameContainsKeywordsPredicate(FindType findType, List<String> keywords) {
        super(findType, keywords);
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        Predicate<String> predicate =
            keyword -> StringUtil.containsPartialIgnoreCase(trackedItem.getName().fullName, keyword);
        return testPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof NameContainsKeywordsPredicate) // instanceof handles nulls
                && super.equals(other));
    }
}
