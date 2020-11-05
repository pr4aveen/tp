package seedu.momentum.model.project.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.BENSON;

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
    private static final List<String> NO_MATCHING_KEYWORD = Collections.singletonList("asfsfd");

    private static final CompletionStatusPredicate isCompletedPredicate =
            new CompletionStatusPredicate(FindType.ALL, COMPLETED_KEYWORD);
    private static final CompletionStatusPredicate isIncompletePredicate =
            new CompletionStatusPredicate(FindType.ALL, INCOMPLETE_KEYWORD);

    @Test
    public void constructor_assertionError() {
        // Multiple keywords -> assertion error
        assertThrows(IllegalArgumentException.class, () -> new CompletionStatusPredicate(FindType.ALL, MULTIPLE_KEYWORDS));
    }

    @Test
    public void isValid() {
        assertTrue(CompletionStatusPredicate.isValid(COMPLETED_KEYWORD));
        assertTrue(CompletionStatusPredicate.isValid(INCOMPLETE_KEYWORD));
        assertFalse(CompletionStatusPredicate.isValid(MULTIPLE_KEYWORDS));
        assertFalse(CompletionStatusPredicate.isValid(NO_MATCHING_KEYWORD));
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(isCompletedPredicate.equals(isCompletedPredicate));
        assertTrue(isIncompletePredicate.equals(isIncompletePredicate));

        // different types -> returns false
        assertFalse(isCompletedPredicate.equals(1));
        assertFalse(isIncompletePredicate.equals(1));

        // null -> returns false
        assertFalse(isIncompletePredicate.equals(null));
        assertFalse(isCompletedPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(isCompletedPredicate.equals(isIncompletePredicate));
    }

    @Test
    public void test() {
        assertTrue(isIncompletePredicate.test(ALICE));
        assertFalse(isCompletedPredicate.test(ALICE));

        assertFalse(isIncompletePredicate.test(BENSON));
        assertTrue(isCompletedPredicate.test(BENSON));
    }
}
