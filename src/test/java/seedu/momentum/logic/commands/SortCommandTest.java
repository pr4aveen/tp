package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_ALPHA_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_ASCENDING_ORDER;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_COMPLETION_STATUS_OFF;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_COMPLETION_STATUS_ON;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_CREATED_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DEADLINE_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DEFAULT_TYPE;
import static seedu.momentum.logic.commands.SortCommand.OUTPUT_DESCENDING_ORDER;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.ALPHA_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.CREATED_DATE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_ASCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_DESCENDING_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.DEADLINE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.DEFAULT_SORT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND;
import static seedu.momentum.testutil.SortCommandUtil.NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.comparators.SortType;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private static final String EMPTY_STRING = "";

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(ALPHA_ASCENDING_COMMAND.equals(ALPHA_ASCENDING_COMMAND));

        // same values -> returns true
        SortCommand alphaAscending = new SortCommand(SortType.ALPHA, true, false, false);
        assertTrue(ALPHA_ASCENDING_COMMAND.equals(alphaAscending));

        // both default -> returns true
        // DEFAULT_SORT sort type is set to SortType.ALPHA, ascending order.
        SortCommand defaultSort = new SortCommand(SortType.ALPHA, true, true, false);
        assertTrue(DEFAULT_SORT_COMMAND.equals(defaultSort));

        // one default, one not default
        assertFalse(ALPHA_ASCENDING_COMMAND.equals(DEFAULT_SORT_COMMAND));

        // different types -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND.equals(1));

        // null -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND.equals(null));

        // different sort types -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND.equals(DEADLINE_ASCENDING_COMMAND));

        // different sort orders -> returns false
        assertFalse(ALPHA_ASCENDING_COMMAND.equals(ALPHA_DESCENDING_COMMAND));
    }

    @Test
    public void execute_defaultSort_sortedInDefaultOrder() {
        expectedModel.updateOrder(SortType.ALPHA, true, false);
        expectedModel.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, EMPTY_STRING, OUTPUT_DEFAULT_TYPE,
                OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(DEFAULT_SORT_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_alphabeticalAscending_sortedInAlphabeticalAscendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.ALPHA, true, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_ALPHA_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(ALPHA_ASCENDING_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_alphabeticalAscendingToggleCompletionStatus_sortedInAlphabeticalAscendingOrder() {
        expectedModel.updateOrder(SortType.ALPHA, true, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_ALPHA_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(ALPHA_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_alphabeticalDescending_sortedInAlphabeticalDescendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.ALPHA, false, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_ALPHA_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(ALPHA_DESCENDING_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_alphabeticalDescendingToggleCompletionStatus_sortedInAlphabeticalDescendingOrder() {
        expectedModel.updateOrder(SortType.ALPHA, false, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_ALPHA_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(ALPHA_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_deadlineAscending_sortedInDeadlineAscendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.DEADLINE, true, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_DEADLINE_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(DEADLINE_ASCENDING_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_deadlineAscendingToggleCompletionStatus_sortedInDeadlineAscendingOrder() {
        expectedModel.updateOrder(SortType.DEADLINE, true, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_DEADLINE_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(
                DEADLINE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_deadlineDescending_sortedInDeadlineDescendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.DEADLINE, false, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_DEADLINE_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(
                DEADLINE_DESCENDING_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_deadlineDescendingToggleCompletionStatus_sortedInDeadlineDescendingOrder() {
        expectedModel.updateOrder(SortType.DEADLINE, false, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_DEADLINE_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(
                DEADLINE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_createdDateAscending_sortedInCreatedDateAscendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.CREATED, true, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_CREATED_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(
                CREATED_DATE_ASCENDING_COMMAND, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_createdDateAscendingToggleCompletionStatus_sortedInCreatedDateAscendingOrder() {
        expectedModel.updateOrder(SortType.CREATED, true, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_CREATED_TYPE, OUTPUT_ASCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(
                CREATED_DATE_ASCENDING_COMMAND_TOGGLE_COMPLETION_STATUS, model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_createdDateDescending_sortedInCreatedDateDescendingOrderWithCompletionStatus() {
        expectedModel.updateOrder(SortType.CREATED, false, false);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_CREATED_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(CREATED_DATE_DESCENDING_COMMAND,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_createdDateDescendingToggleCompletionStatus_sortedInCreatedDateDescendingOrder() {
        expectedModel.updateOrder(SortType.CREATED, false, true);
        expectedModel.commitToHistory();
        String expectedMessage =
                String.format(MESSAGE_SORT_SUCCESS, OUTPUT_CREATED_TYPE, OUTPUT_DESCENDING_ORDER,
                        OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(CREATED_DATE_DESCENDING_COMMAND_TOGGLE_COMPLETION_STATUS,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeAscendingWithCompletionStatus_sortedInCurrentSortAscendingWithCompletionStatus() {
        model.updateOrder(SortType.DEADLINE, false, false);
        expectedModel.updateOrder(SortType.DEADLINE, true, false);
        expectedModel.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, EMPTY_STRING, OUTPUT_ASCENDING_ORDER,
                OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeAscendingNonDefaultToggleCompletionStatus_sortedInCurrentSortTypeAscendingOrder() {
        model.updateOrder(SortType.DEADLINE, false, false);
        expectedModel.updateOrder(SortType.DEADLINE, true, true);
        expectedModel.commitToHistory();
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, EMPTY_STRING, OUTPUT_ASCENDING_ORDER,
                OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(NULL_SORT_TYPE_ASCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeDescending_sortedInCurrentSortTypeDescendingOrderWithCompletionStatus() {

        model.updateOrder(SortType.CREATED, true, false);
        expectedModel.updateOrder(SortType.CREATED, false, false);
        expectedModel.commitToHistory();

        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, EMPTY_STRING, OUTPUT_DESCENDING_ORDER,
                OUTPUT_COMPLETION_STATUS_ON);
        assertCommandSuccess(NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

    @Test
    public void execute_nullSortTypeDescendingToggleCompletionStatus_sortedInCurrentSortTypeDescendingOrder() {

        model.updateOrder(SortType.CREATED, true, false);
        expectedModel.updateOrder(SortType.CREATED, false, true);
        expectedModel.commitToHistory();

        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, EMPTY_STRING, OUTPUT_DESCENDING_ORDER,
                OUTPUT_COMPLETION_STATUS_OFF);
        assertCommandSuccess(NULL_SORT_TYPE_DESCENDING_NON_DEFAULT_COMMAND_TOGGLE_COMPLETION_STATUS,
                model, expectedMessage, expectedModel);
        assertEquals(model.getDisplayList(), expectedModel.getDisplayList());
    }

}
