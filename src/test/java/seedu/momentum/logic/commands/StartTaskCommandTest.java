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

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains unit tests for {@code StartTaskCommand}.
 */
public class StartTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Clock.initFixed(TypicalTimes.DAY);
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        StartCommand startCommand = new StartTaskCommand(INDEX_FIRST, parentProject);

        TrackedItem trackedItemToStart = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        TrackedItem startedTrackedItem = trackedItemToStart.startTimer();
        Project newProject = expectedProject.setTask(trackedItemToStart, startedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        String expectedMessage =
                String.format(StartCommand.MESSAGE_START_TIMER_SUCCESS, INDEX_FIRST.getOneBased())
                        + startedTrackedItem.getTimer().getStartTime().getFormatted();

        assertCommandSuccess(startCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        StartCommand startCommand = new StartTaskCommand(outOfBoundIndex, parentProject);

        assertCommandFailure(startCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyRunning_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        StartCommand startCommand = new StartTaskCommand(INDEX_FIRST, parentProject);
        TrackedItem trackedItemToStart = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Project newProject = parentProject.setTask(trackedItemToStart, trackedItemToStart.startTimer());
        model.setTrackedItem(parentProject, newProject);

        assertCommandFailure(startCommand, model, StartCommand.MESSAGE_EXISTING_TIMER_ERROR);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Clock.initFixed(TypicalTimes.DAY);
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        showTaskAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemToStart = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        TrackedItem startedTrackedItem = trackedItemToStart.startTimer();
        Project newProject = expectedProject.setTask(trackedItemToStart, startedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        StartCommand startCommand = new StartTaskCommand(INDEX_FIRST, parentProject);
        String expectedMessage =
                String.format(StartCommand.MESSAGE_START_TIMER_SUCCESS, INDEX_FIRST.getOneBased())
                        + startedTrackedItem.getTimer().getStartTime().getFormatted();

        showTaskAtIndex(expectedModel, INDEX_FIRST);

        assertCommandSuccess(startCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        showTaskAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < parentProject.getTaskList().size());

        StartCommand startCommand = new StartTaskCommand(outOfBoundIndex, parentProject);

        assertCommandFailure(startCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Project validProject = new ProjectBuilder().build();
        StartCommand startFirstCommand = new StartTaskCommand(INDEX_FIRST, validProject);
        StartCommand startSecondCommand = new StartTaskCommand(INDEX_SECOND, validProject);

        // same object -> returns true
        assertTrue(startFirstCommand.equals(startFirstCommand));

        // same values -> returns true
        StartCommand startFirstCommandCopy = new StartTaskCommand(INDEX_FIRST, validProject);
        assertTrue(startFirstCommand.equals(startFirstCommandCopy));

        // different types -> returns false
        assertFalse(startFirstCommand.equals(1));

        // null -> returns false
        assertFalse(startFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(startFirstCommand.equals(startSecondCommand));
    }
}
