package seedu.momentum.model.project;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.tag.Tag;

/**
 * Tests that a {@code Project}'s {@code Tag} matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate implements Predicate<Project> {
    private final List<String> keywords;
    private final boolean isAllMatch;

    /**
     * Predicate to check whether the {@code Tag} of a {@code Project} contains a
     * certain keyword.
     *
     * @param isAllMatch boolean to indicate whether all keywords need to be successfully matched
     * @param keywords list of keywords to check for matches.
     */
    public TagListContainsKeywordsPredicate(boolean isAllMatch, List<String> keywords) {
        this.isAllMatch = isAllMatch;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Project project) {
        String tagString = buildTagString(project.getTags());
        if (isAllMatch) {
            return keywords.stream()
                    .allMatch(keyword -> StringUtil.containsWordIgnoreCase(tagString, keyword));
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagString, keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagListContainsKeywordsPredicate) other).keywords)) // state check
                && isAllMatch == ((TagListContainsKeywordsPredicate) other).isAllMatch;
    }

    /**
     * Converts a set of {@code Tag} to a string where each entry is followed by a space.
     * This is the format used by {@code StringUtil} methods.
     *
     * @param tags set of tags.
     * @return string representation of tags.
     */
    private String buildTagString(Set<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.tagName);
            sb.append(" ");
        }
        return sb.toString();
    }

}
