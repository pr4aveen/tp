package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

/**
 * Abstract class for all predicates that rely on the FindType. Contains the logic to test
 * predicates based on FindType.
 */
public abstract class ContainsKeywordPredicate implements Predicate<TrackedItem> {
    protected final List<String> keywords;
    protected final FindType findType;

    /**
     * Predicate to check whether a component of a {@code Project} contains a certain keyword.
     *
     * @param findType enum to indicate whether the find type to be used for this find command.
     * @param keywords list of keywords to check for matches.
     */
    public ContainsKeywordPredicate(FindType findType, List<String> keywords) {
        requireAllNonNull(findType, keywords);
        this.findType = findType;
        this.keywords = keywords;
    }

    protected boolean testPredicate(Predicate<String> predicate) {
        requireNonNull(predicate);
        switch (findType) {
        case ALL:
            return keywords.stream().allMatch(predicate);
        case ANY:
            // Fallthrough
        default:
            return keywords.stream().anyMatch(predicate);
        }
    }
}
