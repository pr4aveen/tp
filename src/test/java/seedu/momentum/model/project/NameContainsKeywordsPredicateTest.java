package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.ProjectBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstAnyPredicate =
                new NameContainsKeywordsPredicate(false, firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondAnyPredicate =
                new NameContainsKeywordsPredicate(false, secondPredicateKeywordList);

        NameContainsKeywordsPredicate firstAllPredicate =
                new NameContainsKeywordsPredicate(true, firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondAllPredicate =
                new NameContainsKeywordsPredicate(true, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstAnyPredicate.equals(firstAnyPredicate));
        assertTrue(firstAllPredicate.equals(firstAllPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstAnyPredicateCopy =
                new NameContainsKeywordsPredicate(false, firstPredicateKeywordList);
        assertTrue(firstAnyPredicate.equals(firstAnyPredicateCopy));
        NameContainsKeywordsPredicate firstAllPredicateCopy =
                new NameContainsKeywordsPredicate(true, firstPredicateKeywordList);
        assertTrue(firstAllPredicate.equals(firstAllPredicateCopy));

        // different types -> returns false
        assertFalse(firstAnyPredicate.equals(1));
        assertFalse(firstAllPredicate.equals(1));

        // null -> returns false
        assertFalse(firstAnyPredicate.equals(null));
        assertFalse(firstAllPredicate.equals(null));

        // different project -> returns false
        assertFalse(firstAnyPredicate.equals(secondAnyPredicate));
        assertFalse(firstAllPredicate.equals(secondAllPredicate));
    }

    @Test
    public void test_nameContainsKeywords() {
        // One keyword (Any)
        NameContainsKeywordsPredicate anyPredicate =
                new NameContainsKeywordsPredicate(false, Collections.singletonList("Alice"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Multiple keywords (Any)
        anyPredicate = new NameContainsKeywordsPredicate(false, Arrays.asList("Alice", "Bob"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Only one matching keyword (Any)
        anyPredicate = new NameContainsKeywordsPredicate(false, Arrays.asList("Bob", "Carol"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords (Any)
        anyPredicate = new NameContainsKeywordsPredicate(false, Arrays.asList("aLIce", "bOB"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // One keyword (All)
        NameContainsKeywordsPredicate allPredicate =
                new NameContainsKeywordsPredicate(true, Collections.singletonList("Alice"));
        assertTrue(allPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Multiple keywords (All)
        allPredicate = new NameContainsKeywordsPredicate(true, Arrays.asList("Alice", "Bob"));
        assertTrue(allPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Only one matching keyword (All)
        allPredicate = new NameContainsKeywordsPredicate(true, Arrays.asList("Bob", "Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords (All)
        allPredicate = new NameContainsKeywordsPredicate(true, Arrays.asList("aLIce", "bOB"));
        assertTrue(allPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword (Any)
        NameContainsKeywordsPredicate anyPredicate = new NameContainsKeywordsPredicate(false, Arrays.asList("Carol"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Keywords match description, but does not match name (Any)
        anyPredicate = new NameContainsKeywordsPredicate(false, Arrays.asList("Wonderful"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withName("Alice").withDescription("Wonderful").build()));

        // Non-matching keyword (All)
        NameContainsKeywordsPredicate allPredicate = new NameContainsKeywordsPredicate(true, Arrays.asList("Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withName("Alice Bob").build()));

        // Keywords match description, but does not match name (All)
        allPredicate = new NameContainsKeywordsPredicate(true, Arrays.asList("Wonderful"));
        assertFalse(allPredicate.test(new ProjectBuilder().withName("Alice").withDescription("Wonderful").build()));
    }
}
