package seedu.momentum.model.project.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.project.Project;

/**
 * Tests that a {@code Project}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Project> {
    private final List<String> keywords;
    private final boolean isAllMatch;

    /**
     * Predicate to check whether the {@code Name} of a {@code Project} contains a
     * certain keyword.
     *
     * @param isAllMatch boolean to indicate whether all keywords need to be successfully matched.
     * @param keywords list of keywords to check for matches.
     */
    public NameContainsKeywordsPredicate(boolean isAllMatch, List<String> keywords) {
        this.isAllMatch = isAllMatch;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Project project) {
        if (isAllMatch) {
            return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsPartialIgnoreCase(project.getName().fullName, keyword));
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsPartialIgnoreCase(project.getName().fullName, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)) // state check
                && isAllMatch == ((NameContainsKeywordsPredicate) other).isAllMatch;
    }

}
