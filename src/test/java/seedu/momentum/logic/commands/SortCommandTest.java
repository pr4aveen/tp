package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS_PROJECTS;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_ALPHA_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_ASCENDING_ORDER;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_CREATED_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DEADLINE_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DEFAULT_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DESCENDING_ORDER;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_DESCENDING_COMMAND_WITH_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_ASCENDING_COMMAND_WITH_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.DEFAULT_SORT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.SortType;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private static final String EMPTY_STRING = "";

    private static final Model MODEL = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    private static final Model EXPECTED_MODEL = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS
                .equals(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS));

        // same values -> returns true
        SortCommand alphaAscending = new SortCommand(SortType.ALPHA, true, false, true);
        assertTrue(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS.equals(alphaAscending));

        // both default -> returns true
        // DEFAULT_SORT sort type is set to SortType.ALPHA
        SortCommand defaultSort = new SortCommand(SortType.ALPHA, true, true, true);
        assertTrue(DEFAULT_SORT_COMMAND.equals(defaultSort));

        // one default, one not default
        assertFalse(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS.equals(DEFAULT_SORT_COMMAND));

        // different types -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS.equals(1));

        // null -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS.equals(null));

        // different sort types -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS
                .equals(DEADLINE_ASCENDING_COMMAND_WITH_COMPLETION_STATUS));

        // different sort orders -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS
                .equals(ALPHA_DESCENDING_COMMAND));
    }

    @Test
    public void execute_defaultSort_sortedInDefaultOrder() {
        EXPECTED_MODEL.updateOrder(SortType.ALPHA, true, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS_PROJECTS, EMPTY_STRING, OUTPUT_DEFAULT_TYPE);
        assertCommandSuccess(DEFAULT_SORT_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_alphabeticalAscending_sortedInAlphabeticalAscendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.ALPHA, true, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_ALPHA_TYPE, OUTPUT_ASCENDING_ORDER);
        assertCommandSuccess(ALPHA_ASCENDING_COMMAND_WITH_COMPLETION_STATUS, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_alphabeticalDescending_sortedInAlphabeticalDescendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.ALPHA, false, false);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_ALPHA_TYPE, OUTPUT_DESCENDING_ORDER);
        assertCommandSuccess(ALPHA_DESCENDING_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_deadlineAscending_sortedInDeadlineAscendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.DEADLINE, true, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_DEADLINE_TYPE, OUTPUT_ASCENDING_ORDER);
        assertCommandSuccess(DEADLINE_ASCENDING_COMMAND_WITH_COMPLETION_STATUS, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_deadlineDescending_sortedInDeadlineDescendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.DEADLINE, false, false);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_DEADLINE_TYPE, OUTPUT_DESCENDING_ORDER);
        assertCommandSuccess(DEADLINE_DESCENDING_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_createdDateAscending_sortedInCreatedDateAscendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.CREATED, true, false);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_CREATED_TYPE, OUTPUT_ASCENDING_ORDER);
        assertCommandSuccess(CREATED_DATE_ASCENDING_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_createdDateDescending_sortedInCreatedDateDescendingOrder() {
        EXPECTED_MODEL.updateOrder(SortType.CREATED, false, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS_PROJECTS, OUTPUT_CREATED_TYPE, OUTPUT_DESCENDING_ORDER);
        assertCommandSuccess(CREATED_DATE_DESCENDING_COMMAND_WITH_COMPLETION_STATUS,
                MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeAscendingNonDefault_sortedInCurrentSortTypeAscendingOrder() {
        MODEL.updateOrder(SortType.DEADLINE, false, true);
        EXPECTED_MODEL.updateOrder(SortType.DEADLINE, true, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS_PROJECTS, EMPTY_STRING, OUTPUT_ASCENDING_ORDER);
        assertCommandSuccess(NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeDescendingNonDefault_sortedInCurrentSortTypeDescendingOrder() {
        MODEL.updateOrder(SortType.CREATED, true, true);
        EXPECTED_MODEL.updateOrder(SortType.CREATED, false, true);
        EXPECTED_MODEL.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS_PROJECTS, EMPTY_STRING, OUTPUT_DESCENDING_ORDER);
        assertCommandSuccess(NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND, MODEL, expectedMessage, EXPECTED_MODEL);
        assertEquals(MODEL.getDisplayList(), EXPECTED_MODEL.getDisplayList());
    }

}
