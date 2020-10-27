package seedu.momentum.model.project.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CompletionStatusPredicateTest {

    private static final List<String> COMPLETED_KEYWORD =
            Collections.singletonList(CompletionStatusPredicate.COMPLETED_KEYWORD);
    private static final List<String> INCOMPLETE_KEYWORD =
            Collections.singletonList(CompletionStatusPredicate.INCOMPLETE_KEYWORD);
    private static final List<String> MULTIPLE_KEYWORDS = Arrays.asList(CompletionStatusPredicate.COMPLETED_KEYWORD,
            CompletionStatusPredicate.INCOMPLETE_KEYWORD);
    private static final List<String> NO_MATCHING_KEYWORD = Arrays.asList("asfsfd");

    private static final CompletionStatusPredicate isCompletedPredicateAny =
            new CompletionStatusPredicate(FindType.ANY, COMPLETED_KEYWORD);
    private static final CompletionStatusPredicate isCompletedPredicateAll =
            new CompletionStatusPredicate(FindType.ALL, COMPLETED_KEYWORD);
    private static final CompletionStatusPredicate isIncompletePredicateAny =
            new CompletionStatusPredicate(FindType.ANY, INCOMPLETE_KEYWORD);
    private static final CompletionStatusPredicate isIncompletePredicateAll =
            new CompletionStatusPredicate(FindType.ALL, INCOMPLETE_KEYWORD);

    @Test
    public void constructor_assertionError() {
        // Multiple keywords -> assertion error
        assertThrows(AssertionError.class, () -> new CompletionStatusPredicate(FindType.ANY,
                MULTIPLE_KEYWORDS));

    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(isCompletedPredicateAny.equals(isCompletedPredicateAny));
        assertTrue(isIncompletePredicateAll.equals(isIncompletePredicateAll));

        // same keywords -> returns true
        assertTrue(isCompletedPredicateAny.equals(isCompletedPredicateAll));
        assertTrue(isIncompletePredicateAll.equals(isIncompletePredicateAny));

        // different types -> returns false
        assertFalse(isCompletedPredicateAny.equals(1));
        assertFalse(isIncompletePredicateAll.equals(1));

        // null -> returns false
        assertFalse(isIncompletePredicateAny.equals(null));
        assertFalse(isCompletedPredicateAll.equals(null));

        // different predicate -> returns false
        assertFalse(isCompletedPredicateAll.equals(isIncompletePredicateAll));
        assertFalse(isCompletedPredicateAny.equals(isIncompletePredicateAny));
    }

    @Test
    public void test() {
        assertTrue(isIncompletePredicateAll.test(ALICE));
        assertFalse(isCompletedPredicateAny.test(ALICE));

        // No matching keyword -> return false
        CompletionStatusPredicate noMatchingKeywordsPredicate = new CompletionStatusPredicate(FindType.ANY,
                NO_MATCHING_KEYWORD);
        assertFalse(noMatchingKeywordsPredicate.test(ALICE));
    }
}
