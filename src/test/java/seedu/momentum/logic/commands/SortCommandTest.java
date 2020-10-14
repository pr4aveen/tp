package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
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

    private static final SortCommand DEFAULT_SORT =
            new SortCommand(SortType.NULL, true, true);
    private static final SortCommand ALPHA_ASCENDING =
            new SortCommand(SortType.ALPHA, true, false);
    private static final SortCommand ALPHA_DESCENDING =
            new SortCommand(SortType.ALPHA, false, false);
    private static final SortCommand DEADLINE_ASCENDING =
            new SortCommand(SortType.DEADLINE, true, false);
    private static final SortCommand DEADLINE_DESCENDING =
            new SortCommand(SortType.DEADLINE, false, false);
    private static final SortCommand CREATED_DATE_ASCENDING =
            new SortCommand(SortType.CREATED, true, false);
    private static final SortCommand CREATED_DATE_DESCENDING =
            new SortCommand(SortType.CREATED, false, false);
    private static final SortCommand NULL_SORT_TYPE_ASCENDING_NON_DEFAULT =
            new SortCommand(SortType.NULL, true, false);
    private static final SortCommand NULL_SORT_TYPE_DESCENDING_NON_DEFAULT =
            new SortCommand(SortType.NULL, false, false);

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(ALPHA_ASCENDING.equals(ALPHA_ASCENDING));

        // same values -> returns true
        SortCommand alphaAscending = new SortCommand(SortType.ALPHA, true, false);
        assertTrue(ALPHA_ASCENDING.equals(alphaAscending));

        // both default -> returns true
        // DEFAULT_SORT sort type is set to SortType.ALPHA
        SortCommand defaultSort = new SortCommand(SortType.ALPHA, true, true);
        assertTrue(DEFAULT_SORT.equals(defaultSort));

        // one default, one not default
        assertFalse(ALPHA_ASCENDING.equals(DEFAULT_SORT));

        // different types -> returns false
        assertFalse(ALPHA_ASCENDING.equals(1));

        // null -> returns false
        assertFalse(ALPHA_ASCENDING.equals(null));

        // different sort types -> returns false
        assertFalse(ALPHA_ASCENDING.equals(DEADLINE_ASCENDING));

        // different sort orders -> returns false
        assertFalse(ALPHA_ASCENDING.equals(ALPHA_DESCENDING));
    }

    @Test
    public void execute_defaultSort_sortedInDefaultOrder() {
        expectedModel.orderFilteredProjectList(SortType.ALPHA, true);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "", "default alphabetical, ascending");
        assertCommandSuccess(DEFAULT_SORT, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_alphabeticalAscending_sortedInAlphabeticalAscendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.ALPHA, true);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "alphabetical, ", "ascending");
        assertCommandSuccess(ALPHA_ASCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_alphabeticalDescending_sortedInAlphabeticalDescendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.ALPHA, false);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "alphabetical, ", "descending");
        assertCommandSuccess(ALPHA_DESCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_deadlineAscending_sortedInDeadlineAscendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.DEADLINE, true);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "deadline, ", "ascending");
        assertCommandSuccess(DEADLINE_ASCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_deadlineDescending_sortedInDeadlineDescendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.DEADLINE, false);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "deadline, ", "descending");
        assertCommandSuccess(DEADLINE_DESCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_createdDateAscending_sortedInCreatedDateAscendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.CREATED, true);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "created date, ", "ascending");
        assertCommandSuccess(CREATED_DATE_ASCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_createdDateDescending_sortedInCreatedDateDescendingOrder() {
        expectedModel.orderFilteredProjectList(SortType.CREATED, false);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "created date, ", "descending");
        assertCommandSuccess(CREATED_DATE_DESCENDING, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_nullSortTypeAscendingNonDefault_sortedInCurrentSortTypeAscendingOrder() {
        model.orderFilteredProjectList(SortType.DEADLINE, false);
        expectedModel.orderFilteredProjectList(SortType.DEADLINE, true);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "", "ascending");
        assertCommandSuccess(NULL_SORT_TYPE_ASCENDING_NON_DEFAULT, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

    @Test
    public void execute_nullSortTypeDescendingNonDefault_sortedInCurrentSortTypeDscendingOrder() {
        model.orderFilteredProjectList(SortType.CREATED, true);
        expectedModel.orderFilteredProjectList(SortType.CREATED, false);
        String expectedMessage = String.format(MESSAGE_SORT_SUCCESS, "", "descending");
        assertCommandSuccess(NULL_SORT_TYPE_DESCENDING_NON_DEFAULT, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredProjectList(), expectedModel.getFilteredProjectList());
    }

}
