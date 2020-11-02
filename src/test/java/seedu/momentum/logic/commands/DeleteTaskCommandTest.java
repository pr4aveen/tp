package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Task taskToDelete = (Task) parentProject.getTaskList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        DeleteTaskCommand deleteCommand = new DeleteTaskCommand(INDEX_FIRST, parentProject);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Project newProject = expectedProject.deleteTask(taskToDelete);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Project viewProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(viewProject);
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        DeleteCommand deleteCommand = new DeleteTaskCommand(outOfBoundIndex, viewProject);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        showTaskAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemToDelete = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteTaskCommand(INDEX_FIRST, parentProject);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, trackedItemToDelete);

        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        Project newProject = expectedProject.deleteTask(trackedItemToDelete);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Project viewProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(viewProject);
        showTaskAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < viewProject.getTaskList().size());

        DeleteCommand deleteCommand = new DeleteTaskCommand(outOfBoundIndex, viewProject);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Project validProject = new ProjectBuilder().build();
        DeleteCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST, validProject);
        DeleteCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND, validProject);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST, validProject);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updatePredicate(p -> false);

        assertTrue(model.getDisplayList().isEmpty());
    }
}
