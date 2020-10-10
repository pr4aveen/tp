package seedu.momentum.model.project;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.momentum.testutil.ProjectBuilder;

public class TagListContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagListContainsKeywordsPredicate firstAnyPredicate =
                new TagListContainsKeywordsPredicate(false, firstPredicateKeywordList);
        TagListContainsKeywordsPredicate secondAnyPredicate =
                new TagListContainsKeywordsPredicate(false, secondPredicateKeywordList);

        TagListContainsKeywordsPredicate firstAllPredicate =
                new TagListContainsKeywordsPredicate(true, firstPredicateKeywordList);
        TagListContainsKeywordsPredicate secondAllPredicate =
                new TagListContainsKeywordsPredicate(true, secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstAnyPredicate.equals(firstAnyPredicate));
        assertTrue(firstAllPredicate.equals(firstAllPredicate));

        // same values -> returns true
        TagListContainsKeywordsPredicate firstAnyPredicateCopy =
                new TagListContainsKeywordsPredicate(false, firstPredicateKeywordList);
        assertTrue(firstAnyPredicate.equals(firstAnyPredicateCopy));
        TagListContainsKeywordsPredicate firstAllPredicateCopy =
                new TagListContainsKeywordsPredicate(true, firstPredicateKeywordList);
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
        TagListContainsKeywordsPredicate anyPredicate =
                new TagListContainsKeywordsPredicate(false, Collections.singletonList("Alice"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Multiple keywords (Any)
        anyPredicate = new TagListContainsKeywordsPredicate(false, Arrays.asList("Alice", "Bob"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Only one matching keyword (Any)
        anyPredicate = new TagListContainsKeywordsPredicate(false, Arrays.asList("Bob", "Carol"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withTags("Alice", "Carol").build()));

        // Mixed-case keywords (Any)
        anyPredicate = new TagListContainsKeywordsPredicate(false, Arrays.asList("aLIce", "bOB"));
        assertTrue(anyPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // One keyword (All)
        TagListContainsKeywordsPredicate allPredicate =
                new TagListContainsKeywordsPredicate(true, Collections.singletonList("Alice"));
        assertTrue(allPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Multiple keywords (All)
        allPredicate = new TagListContainsKeywordsPredicate(true, Arrays.asList("Alice", "Bob"));
        assertTrue(allPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Only one matching keyword (All)
        allPredicate = new TagListContainsKeywordsPredicate(true, Arrays.asList("Bob", "Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withTags("Alice", "Carol").build()));

        // Mixed-case keywords (All)
        allPredicate = new TagListContainsKeywordsPredicate(true, Arrays.asList("aLIce", "bOB"));
        assertTrue(allPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword (Any)
        TagListContainsKeywordsPredicate anyPredicate =
                new TagListContainsKeywordsPredicate(false, Arrays.asList("Carol"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Keywords match description, but does not match tags (Any)
        anyPredicate = new TagListContainsKeywordsPredicate(false, Arrays.asList("Wonderful"));
        assertFalse(anyPredicate.test(new ProjectBuilder().withTags("Alice").withDescription("Wonderful").build()));

        // Non-matching keyword (All)
        TagListContainsKeywordsPredicate allPredicate =
                new TagListContainsKeywordsPredicate(true, Arrays.asList("Carol"));
        assertFalse(allPredicate.test(new ProjectBuilder().withTags("Alice", "Bob").build()));

        // Keywords match description, but does not match tags (All)
        allPredicate = new TagListContainsKeywordsPredicate(true, Arrays.asList("Wonderful"));
        assertFalse(allPredicate.test(new ProjectBuilder().withTags("Alice").withDescription("Wonderful").build()));
    }
}
