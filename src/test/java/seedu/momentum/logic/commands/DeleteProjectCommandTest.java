package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.logic.commands.CommandTestUtil.showTrackedItemWithName;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.predicates.FindType;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteProjectCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        TrackedItem trackedItemToDelete = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        DeleteProjectCommand deleteCommand = new DeleteProjectCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PROJECT_SUCCESS, trackedItemToDelete);

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.deleteTrackedItem(trackedItemToDelete);
        expectedModel.commitToHistory();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        DeleteCommand deleteCommand = new DeleteProjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemToDelete = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteProjectCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PROJECT_SUCCESS, trackedItemToDelete);

        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.deleteTrackedItem(trackedItemToDelete);

        showTrackedItemWithName(expectedModel, trackedItemToDelete.getName());

        expectedModel.commitToHistory();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showProjectAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectBook().getTrackedItemList().size());

        DeleteCommand deleteCommand = new DeleteProjectCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteProjectCommand(INDEX_FIRST);
        DeleteCommand deleteSecondCommand = new DeleteProjectCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteProjectCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoProject(Model model) {
        model.updatePredicate(p -> false);

        assertTrue(model.getDisplayList().isEmpty());
    }
}
