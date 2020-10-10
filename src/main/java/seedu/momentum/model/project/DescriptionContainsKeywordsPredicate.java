package seedu.momentum.model.project;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;

/**
 * Tests that a {@code Project}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Project> {
    private final List<String> keywords;
    private final boolean isAllMatch;

    /**
     * Predicate to check whether the {@code Description} of a {@code Project} contains a
     * certain keyword.
     *
     * @param isAllMatch boolean to indicate whether all keywords need to be successfully matched
     * @param keywords list of keywords to check for matches.
     */
    public DescriptionContainsKeywordsPredicate(boolean isAllMatch, List<String> keywords) {
        this.isAllMatch = isAllMatch;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Project project) {
        if (isAllMatch) {
            return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsPartialIgnoreCase(project.getDescription().value, keyword));
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsPartialIgnoreCase(project.getDescription().value, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)) // state check
                && isAllMatch == ((DescriptionContainsKeywordsPredicate) other).isAllMatch;
    }

}
