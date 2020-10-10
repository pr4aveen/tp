package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.ProjectBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DescriptionContainsKeywordsPredicate firstAnyPredicate =
                new DescriptionContainsKeywordsPredicate(false, firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondAnyPredicate =
                new DescriptionContainsKeywordsPredicate(false, secondPredicateKeywordList);

        DescriptionContainsKeywordsPredicate firstAllPredicate =
                new DescriptionContainsKeywordsPredicate(true, firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondAllPredicate =
                new DescriptionContainsKeywordsPredicate(true, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstAnyPredicate.equals(firstAnyPredicate));
        assertTrue(firstAllPredicate.equals(firstAllPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstAnyPredicateCopy =
                new DescriptionContainsKeywordsPredicate(false, firstPredicateKeywordList);
        assertTrue(firstAnyPredicate.equals(firstAnyPredicateCopy));
        DescriptionContainsKeywordsPredicate firstAllPredicateCopy =
                new DescriptionContainsKeywordsPredicate(true, firstPredicateKeywordList);
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
        DescriptionContainsKeywordsPredicate anyPredicate =
                new DescriptionContainsKeywordsPredicate(false, Collections.singletonList("Alice"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Multiple keywords (Any)
        anyPredicate = new DescriptionContainsKeywordsPredicate(false, Arrays.asList("Alice", "Bob"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Only one matching keyword (Any)
        anyPredicate = new DescriptionContainsKeywordsPredicate(false, Arrays.asList("Bob", "Carol"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withDescription("Alice Carol").build()));

        // Mixed-case keywords (Any)
        anyPredicate = new DescriptionContainsKeywordsPredicate(false, Arrays.asList("aLIce", "bOB"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // One keyword (All)
        DescriptionContainsKeywordsPredicate allPredicate =
                new DescriptionContainsKeywordsPredicate(true, Collections.singletonList("Alice"));
        assertTrue(allPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Multiple keywords (All)
        allPredicate = new DescriptionContainsKeywordsPredicate(true, Arrays.asList("Alice", "Bob"));
        assertTrue(allPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Only one matching keyword (All)
        allPredicate = new DescriptionContainsKeywordsPredicate(true, Arrays.asList("Bob", "Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withDescription("Alice Carol").build()));

        // Mixed-case keywords (All)
        allPredicate = new DescriptionContainsKeywordsPredicate(true, Arrays.asList("aLIce", "bOB"));
        assertTrue(allPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword (Any)
        DescriptionContainsKeywordsPredicate anyPredicate =
                new DescriptionContainsKeywordsPredicate(false, Arrays.asList("Carol"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Keywords match name, but does not match description (Any)
        anyPredicate = new DescriptionContainsKeywordsPredicate(false, Arrays.asList("Alice"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withName("Alice").withDescription("Wonderful").build()));

        // Non-matching keyword (All)
        DescriptionContainsKeywordsPredicate allPredicate =
                new DescriptionContainsKeywordsPredicate(true, Arrays.asList("Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withDescription("Alice Bob").build()));

        // Keywords match name, but does not match description (All)
        allPredicate = new DescriptionContainsKeywordsPredicate(true, Arrays.asList("Alice"));
        assertFalse(allPredicate.test(new ProjectBuilder().withName("Alice").withDescription("Wonderful").build()));
    }
}
