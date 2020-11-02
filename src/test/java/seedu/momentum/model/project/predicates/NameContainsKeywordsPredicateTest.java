package seedu.momentum.model.project.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class NameContainsKeywordsPredicateTest {

    private static final List<String> SINGLE_KEYWORD = Collections.singletonList("Alice");
    private static final List<String> MULTIPLE_KEYWORDS = Arrays.asList("Alice", "Pauline");
    private static final List<String> ONE_MATCHING_KEYWORD = Arrays.asList("Alice", "Bob");
    private static final List<String> MIXED_CASE_KEYWORDS = Arrays.asList("AlIcE", "PaUlInE");
    private static final List<String> NO_MATCHING_KEYWORDS = Arrays.asList("nothing", "matches");
    private static final List<String> ONLY_MATCHES_DESCRIPTION = Arrays.asList("Likes", "Coding");
    private static final List<String> ONLY_MATCHES_TAG = Collections.singletonList("friends");

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstAnyPredicate =
                new NameContainsKeywordsPredicate(FindType.ANY, firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondAnyPredicate =
                new NameContainsKeywordsPredicate(FindType.ANY, secondPredicateKeywordList);

        NameContainsKeywordsPredicate firstAllPredicate =
                new NameContainsKeywordsPredicate(FindType.ALL, firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondAllPredicate =
                new NameContainsKeywordsPredicate(FindType.ALL, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstAnyPredicate.equals(firstAnyPredicate));
        assertTrue(firstAllPredicate.equals(firstAllPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstAnyPredicateCopy =
                new NameContainsKeywordsPredicate(FindType.ANY, firstPredicateKeywordList);
        assertTrue(firstAnyPredicate.equals(firstAnyPredicateCopy));
        NameContainsKeywordsPredicate firstAllPredicateCopy =
                new NameContainsKeywordsPredicate(FindType.ALL, firstPredicateKeywordList);
        assertTrue(firstAllPredicate.equals(firstAllPredicateCopy));

        // different types -> returns false
        assertFalse(firstAnyPredicate.equals(1));
        assertFalse(firstAllPredicate.equals(1));

        // null -> returns false
        assertFalse(firstAnyPredicate.equals(null));
        assertFalse(firstAllPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstAnyPredicate.equals(secondAnyPredicate));
        assertFalse(firstAllPredicate.equals(secondAllPredicate));

        // different findType -> returns false
        assertFalse(firstAnyPredicate.equals(firstAllPredicate));
        assertFalse(secondAnyPredicate.equals(secondAllPredicate));
    }

    @Test
    public void test_nameContainsKeywords() {
        // One keyword (Any)
        NameContainsKeywordsPredicate anyPredicate =
                new NameContainsKeywordsPredicate(FindType.ANY, SINGLE_KEYWORD);
        assertTrue(anyPredicate.test(ALICE));

        // Multiple keywords (Any)
        anyPredicate = new NameContainsKeywordsPredicate(FindType.ANY, MULTIPLE_KEYWORDS);
        assertTrue(anyPredicate.test(ALICE));

        // Only one matching keyword (Any)
        anyPredicate = new NameContainsKeywordsPredicate(FindType.ANY, ONE_MATCHING_KEYWORD);
        assertTrue(anyPredicate.test(ALICE));

        // Mixed-case keywords (Any)
        anyPredicate = new NameContainsKeywordsPredicate(FindType.ANY, MIXED_CASE_KEYWORDS);
        assertTrue(anyPredicate.test(ALICE));

        // One keyword (All)
        NameContainsKeywordsPredicate allPredicate =
                new NameContainsKeywordsPredicate(FindType.ALL, SINGLE_KEYWORD);
        assertTrue(allPredicate.test(ALICE));

        // Multiple keywords (All)
        allPredicate = new NameContainsKeywordsPredicate(FindType.ALL, MULTIPLE_KEYWORDS);
        assertTrue(allPredicate.test(ALICE));

        // Only one matching keyword (All)
        allPredicate = new NameContainsKeywordsPredicate(FindType.ALL, ONE_MATCHING_KEYWORD);
        assertFalse(allPredicate.test(ALICE));

        // Mixed-case keywords (All)
        allPredicate = new NameContainsKeywordsPredicate(FindType.ALL, MIXED_CASE_KEYWORDS);
        assertTrue(allPredicate.test(ALICE));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword (Any)
        NameContainsKeywordsPredicate anyPredicate =
                new NameContainsKeywordsPredicate(FindType.ANY, NO_MATCHING_KEYWORDS);
        assertFalse(anyPredicate.test(ALICE));

        // Keywords match description, but does not match name (Any)
        anyPredicate = new NameContainsKeywordsPredicate(FindType.ANY, ONLY_MATCHES_DESCRIPTION);
        assertFalse(anyPredicate.test(ALICE));

        // Keywords match tag, but does not match name (Any)
        anyPredicate = new NameContainsKeywordsPredicate(FindType.ANY, ONLY_MATCHES_DESCRIPTION);
        assertFalse(anyPredicate.test(ALICE));

        // Non-matching keyword (All)
        NameContainsKeywordsPredicate allPredicate =
                new NameContainsKeywordsPredicate(FindType.ALL, NO_MATCHING_KEYWORDS);
        assertFalse(allPredicate.test(ALICE));

        // Keywords match description, but does not match name (All)
        allPredicate = new NameContainsKeywordsPredicate(FindType.ALL, ONLY_MATCHES_TAG);
        assertFalse(allPredicate.test(ALICE));

        // Keywords match tag, but does not match name (All)
        allPredicate = new NameContainsKeywordsPredicate(FindType.ALL, ONLY_MATCHES_TAG);
        assertFalse(allPredicate.test(ALICE));
    }
}
