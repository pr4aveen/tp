package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.commons.core.Messages.MESSAGE_PROJECTS_LISTED_OVERVIEW;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.parser.FindCommandParser.FIND_ARGUMENT_DELIMITER;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
import static seedu.momentum.testutil.TypicalProjects.BENSON;
import static seedu.momentum.testutil.TypicalProjects.CARL;
import static seedu.momentum.testutil.TypicalProjects.DANIEL;
import static seedu.momentum.testutil.TypicalProjects.ELLE;
import static seedu.momentum.testutil.TypicalProjects.FIONA;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.predicates.DescriptionContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.model.project.predicates.TagListContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private static final String TEST_NAMES = "Kurz Elle Kunz";
    private static final String TEST_DESCRIPTIONS = "starbucks elephants cats";
    private static final String TEST_TAGS = "friends owesMoney";

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(false, Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(false, Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(false, " ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void anyMatch_multipleNameKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(false, TEST_NAMES);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredProjectList());
    }

    @Test
    public void allMatch_multipleNameKeywords_noProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(true, TEST_NAMES);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void allMatch_multipleNameKeywords_oneProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(true, "CA rL Ku Rz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(CARL), model.getFilteredProjectList());
    }

    @Test
    public void anyMatch_multipleDescriptionKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 3);
        DescriptionContainsKeywordsPredicate predicate =
                prepareDescriptionPredicate(false, TEST_DESCRIPTIONS);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ELLE, FIONA), model.getFilteredProjectList());
    }

    @Test
    public void allMatch_multipleDescriptionKeywords_noProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate =
                prepareDescriptionPredicate(true, TEST_DESCRIPTIONS);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredProjectList());
    }

    @Test
    public void allMatch_multipleDescriptionKeywords_oneProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 1);
        DescriptionContainsKeywordsPredicate predicate =
                prepareDescriptionPredicate(true, "likes star bucks");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(FIONA), model.getFilteredProjectList());
    }

    @Test
    public void anyMatch_multipleTagKeywords_multipleProjectsFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 3);
        TagListContainsKeywordsPredicate predicate = prepareTagListPredicate(false, TEST_TAGS);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredProjectList());
    }

    @Test
    public void allMatch_multipleTagKeywords_oneProjectFound() {
        String expectedMessage = String.format(MESSAGE_PROJECTS_LISTED_OVERVIEW, 1);
        TagListContainsKeywordsPredicate predicate = prepareTagListPredicate(true, TEST_TAGS);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredProjectList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredProjectList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(boolean isAllMatch, String userInput) {
        return new NameContainsKeywordsPredicate(isAllMatch, Arrays.asList(userInput.split(FIND_ARGUMENT_DELIMITER)));
    }

    /**
     * Parses {@code userInput} into a {@code DescriptionContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate prepareDescriptionPredicate(boolean isAllMatch, String userInput) {
        return new DescriptionContainsKeywordsPredicate(isAllMatch,
            Arrays.asList(userInput.split(FIND_ARGUMENT_DELIMITER)));
    }

    /**
     * Parses {@code userInput} into a {@code TagListContainsKeywordsPredicate}.
     */
    private TagListContainsKeywordsPredicate prepareTagListPredicate(boolean isAllMatch, String userInput) {
        return new TagListContainsKeywordsPredicate(isAllMatch,
            Arrays.asList(userInput.split(FIND_ARGUMENT_DELIMITER)));
    }
}
