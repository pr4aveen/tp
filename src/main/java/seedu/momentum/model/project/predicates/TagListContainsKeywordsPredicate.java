package seedu.momentum.model.project.predicates;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.tag.Tag;

/**
 * Tests that a {@code TrackedItem}'s {@code Tag} matches any of the keywords given.
 */
public class TagListContainsKeywordsPredicate implements Predicate<TrackedItem> {
    private final List<String> keywords;
    private final FindType findType;

    /**
     * Creates a predicate to check whether the {@code Tag} of a {@code Project} contains a
     * certain keyword.
     *
     * @param findType enum to indicate the find type to be used for this find command.
     * @param keywords list of keywords to check for matches.
     */
    public TagListContainsKeywordsPredicate(FindType findType, List<String> keywords) {
        this.findType = findType;
        this.keywords = keywords;
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        String tagString = buildTagString(trackedItem.getTags());
        Predicate<String> predicate = keyword -> StringUtil.containsWordIgnoreCase(tagString, keyword);
        switch (findType) {
        case ALL:
            return keywords.stream().allMatch(predicate);
        case ANY:
            // Fallthrough
        default:
            return keywords.stream().anyMatch(predicate);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagListContainsKeywordsPredicate) other).keywords)) // state check
                && findType == ((TagListContainsKeywordsPredicate) other).findType;
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
