//@@author pr4aveen
package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.momentum.commons.util.StringUtil;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.tag.Tag;

/**
 * Tests that a {@code TrackedItem}'s {@code Tag} matches any of the keywords given.
 */
public class TagListContainsKeywordPredicate extends ContainsKeywordPredicate {

    /**
     * Creates a predicate to check whether the {@code Tag} of a {@code Project} contains a
     * certain keyword.
     *
     * @param findType Enum to indicate the find type to be used for this find command.
     * @param keywords List of keywords to check for matches.
     */
    public TagListContainsKeywordPredicate(FindType findType, List<String> keywords) {
        super(findType, keywords);
    }

    @Override
    public boolean test(TrackedItem trackedItem) {
        requireNonNull(trackedItem);
        String tagString = buildTagString(trackedItem.getTags());
        Predicate<String> predicate = keyword -> StringUtil.containsWordIgnoreCase(tagString, keyword);
        return testPredicate(predicate);
    }

    /**
     * Converts a set of {@code Tag} to a string where each entry is followed by a space.
     * This is the format used by {@code StringUtil} methods.
     *
     * @param tags Set of tags.
     * @return String representation of tags.
     */
    private String buildTagString(Set<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.tagName);
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListContainsKeywordPredicate // instanceof handles nulls
                && super.equals(other)); // state check
    }

}
