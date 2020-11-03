package seedu.momentum.model.project.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.momentum.model.project.TrackedItem;

public abstract class FindCommandPredicate implements Predicate<TrackedItem> {
    protected final List<String> keywords;
    protected final FindType findType;

    /**
     * Predicate to check whether the {@code Name} or {@code Description} or of a {@code Project} contains a
     * certain keyword.
     *
     * @param findType enum to indicate whether the find type to be used for this find command.
     * @param keywords list of keywords to check for matches.
     */
    public FindCommandPredicate(FindType findType, List<String> keywords) {
        this.findType = findType;
        this.keywords = keywords;
    }

    protected boolean testPredicate(Predicate<String> predicate) {
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
