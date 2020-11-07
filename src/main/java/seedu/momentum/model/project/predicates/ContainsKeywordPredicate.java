//@@author pr4aveen
package seedu.momentum.model.project.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Abstract class for all predicates that rely on the FindType. Contains the logic to test
 * predicates based on FindType.
 */
public abstract class ContainsKeywordPredicate implements MomentumPredicate {
    protected final List<String> keywords;
    protected final FindType findType;

    /**
     * Predicate to check whether a component of a {@code Project} contains a certain keyword.
     *
     * @param findType Enum to indicate whether the find type to be used for this find command.
     * @param keywords List of keywords to check for matches.
     */
    public ContainsKeywordPredicate(FindType findType, List<String> keywords) {
        requireAllNonNull(findType, keywords);
        this.findType = findType;
        this.keywords = keywords;
    }

    /**
     * Tests a given predicate with the specified find type.
     *
     * @param predicate Predicate to be tested.
     * @return Result of the test.
     */
    protected boolean testPredicate(Predicate<String> predicate) {
        requireNonNull(predicate);
        switch (findType) {
        case ALL:
            return keywords.stream().allMatch(predicate);
        case NONE:
            return keywords.stream().noneMatch(predicate);
        case ANY:
            // Fallthrough
        default:
            return keywords.stream().anyMatch(predicate);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, findType);
    }

    @Override
    public boolean isSamePredicate(MomentumPredicate other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((ContainsKeywordPredicate) other).keywords)) // state check
                && findType == ((ContainsKeywordPredicate) other).findType;
    }
}
