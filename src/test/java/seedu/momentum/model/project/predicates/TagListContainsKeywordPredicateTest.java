package seedu.momentum.model.project.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.testutil.TypicalProjects.BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TagListContainsKeywordPredicateTest {

    private static final List<String> SINGLE_KEYWORD = Collections.singletonList("friends");
    private static final List<String> MULTIPLE_KEYWORDS = Arrays.asList("friends", "owesMoney");
    private static final List<String> ONE_MATCHING_KEYWORD = Arrays.asList("dogs", "owesMoney");
    private static final List<String> MIXED_CASE_KEYWORDS = Arrays.asList("fRiEnDs", "oWeSmOneY");
    private static final List<String> NO_MATCHING_KEYWORDS = Arrays.asList("nothing", "matches");
    private static final List<String> ONLY_MATCHES_NAME = Arrays.asList("Benson", "Meier");
    private static final List<String> ONLY_MATCHES_DESCRIPTION = Collections.singletonList("dogs");
    private static final List<String> ONLY_MATCHES_COMPLETION_STATUS = Collections.singletonList("completed");
    private static final List<String> FIRST_KEYWORD_LIST = Collections.singletonList("first");
    private static final List<String> SECOND_KEYWORD_LIST = Arrays.asList("first", "second");

    @Test
    public void equals() {
        TagListContainsKeywordPredicate firstAnyPredicate =
                new TagListContainsKeywordPredicate(FindType.ANY, FIRST_KEYWORD_LIST);
        TagListContainsKeywordPredicate secondAnyPredicate =
                new TagListContainsKeywordPredicate(FindType.ANY, SECOND_KEYWORD_LIST);

        TagListContainsKeywordPredicate firstAllPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, FIRST_KEYWORD_LIST);
        TagListContainsKeywordPredicate secondAllPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, SECOND_KEYWORD_LIST);

        // same object -> returns true
        assertTrue(firstAnyPredicate.equals(firstAnyPredicate));
        assertTrue(firstAllPredicate.equals(firstAllPredicate));

        // same values -> returns true
        TagListContainsKeywordPredicate firstAnyPredicateCopy =
                new TagListContainsKeywordPredicate(FindType.ANY, FIRST_KEYWORD_LIST);
        assertTrue(firstAnyPredicate.equals(firstAnyPredicateCopy));
        TagListContainsKeywordPredicate firstAllPredicateCopy =
                new TagListContainsKeywordPredicate(FindType.ALL, FIRST_KEYWORD_LIST);
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

        // different findType -> return false
        assertFalse(firstAllPredicate.equals(firstAnyPredicate));
        assertFalse(secondAnyPredicate.equals(secondAllPredicate));

        // ContainsKeywordPredicate, same subtype -> returns true
        ContainsKeywordPredicate thirdPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, FIRST_KEYWORD_LIST);
        ContainsKeywordPredicate fourthPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, FIRST_KEYWORD_LIST);
        assertTrue(thirdPredicate.equals(fourthPredicate));

        // ContainsKeywordPredicate, different subtype -> returns false
        fourthPredicate = new DescriptionContainsKeywordsPredicate(FindType.ALL, FIRST_KEYWORD_LIST);
        assertFalse(thirdPredicate.equals(fourthPredicate));

        // ContainsKeywordPredicate, equal to itself -> returns true
        assertTrue(thirdPredicate.equals(thirdPredicate));
    }

    @Test
    public void test_tagListContainsKeywords() {
        // One keyword (Any)
        TagListContainsKeywordPredicate anyPredicate =
                new TagListContainsKeywordPredicate(FindType.ANY, SINGLE_KEYWORD);
        assertTrue(anyPredicate.test(BENSON));

        // Multiple keywords (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, MULTIPLE_KEYWORDS);
        assertTrue(anyPredicate.test(BENSON));

        // Only one matching keyword (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, ONE_MATCHING_KEYWORD);
        assertTrue(anyPredicate.test(BENSON));

        // Mixed-case keywords (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, MIXED_CASE_KEYWORDS);
        assertTrue(anyPredicate.test(BENSON));

        // One keyword (All)
        TagListContainsKeywordPredicate allPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, SINGLE_KEYWORD);
        assertTrue(allPredicate.test(BENSON));

        // Multiple keywords (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, MULTIPLE_KEYWORDS);
        assertTrue(allPredicate.test(BENSON));

        // Only one matching keyword (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, ONE_MATCHING_KEYWORD);
        assertFalse(allPredicate.test(BENSON));

        // Mixed-case keywords (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, MIXED_CASE_KEYWORDS);
        assertTrue(allPredicate.test(BENSON));

        // One keyword (None)
        TagListContainsKeywordPredicate nonePredicate =
                new TagListContainsKeywordPredicate(FindType.NONE, SINGLE_KEYWORD);
        assertFalse(nonePredicate.test(BENSON));

        // Multiple keywords (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, MULTIPLE_KEYWORDS);
        assertFalse(nonePredicate.test(BENSON));

        // Only one matching keyword (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, ONE_MATCHING_KEYWORD);
        assertFalse(nonePredicate.test(BENSON));

        // Mixed-case keywords (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, MIXED_CASE_KEYWORDS);
        assertFalse(nonePredicate.test(BENSON));
    }

    @Test
    public void test_tagListDoesNotContainKeywords() {
        // Non-matching keyword (Any)
        TagListContainsKeywordPredicate anyPredicate =
                new TagListContainsKeywordPredicate(FindType.ANY, NO_MATCHING_KEYWORDS);
        assertFalse(anyPredicate.test(BENSON));

        // Keywords match name, but does not match tags (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, ONLY_MATCHES_NAME);
        assertFalse(anyPredicate.test(BENSON));

        // Keywords match description, but does not match tags (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, ONLY_MATCHES_DESCRIPTION);
        assertFalse(anyPredicate.test(BENSON));

        // Keywords match completion status, but does not match tags (Any)
        anyPredicate = new TagListContainsKeywordPredicate(FindType.ANY, ONLY_MATCHES_COMPLETION_STATUS);
        assertFalse(anyPredicate.test(BENSON));

        // Non-matching keyword (All)
        TagListContainsKeywordPredicate allPredicate =
                new TagListContainsKeywordPredicate(FindType.ALL, NO_MATCHING_KEYWORDS);
        assertFalse(allPredicate.test(BENSON));

        // Keywords match name, but does not match tags (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, ONLY_MATCHES_NAME);
        assertFalse(allPredicate.test(BENSON));

        // Keywords match description, but does not match tags (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, ONLY_MATCHES_DESCRIPTION);
        assertFalse(allPredicate.test(BENSON));

        // Keywords match completion status, but does not match tags (All)
        allPredicate = new TagListContainsKeywordPredicate(FindType.ALL, ONLY_MATCHES_COMPLETION_STATUS);
        assertFalse(allPredicate.test(BENSON));

        // Non-matching keyword (None)
        TagListContainsKeywordPredicate nonePredicate =
                new TagListContainsKeywordPredicate(FindType.NONE, NO_MATCHING_KEYWORDS);
        assertTrue(nonePredicate.test(BENSON));

        // Keywords match name, but does not match tags (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, ONLY_MATCHES_NAME);
        assertTrue(nonePredicate.test(BENSON));

        // Keywords match description, but does not match tags (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, ONLY_MATCHES_DESCRIPTION);
        assertTrue(nonePredicate.test(BENSON));

        // Keywords match completion status, but does not match tags (None)
        nonePredicate = new TagListContainsKeywordPredicate(FindType.NONE, ONLY_MATCHES_COMPLETION_STATUS);
        assertTrue(nonePredicate.test(BENSON));
    }
}
