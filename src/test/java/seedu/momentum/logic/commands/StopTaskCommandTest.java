package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.momentum.logic.commands.CommandTestUtil.showTrackedItemWithName;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.predicates.NameContainsKeywordsPredicate;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains unit tests for {@code StartCommand}.
 */
public class StopTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Clock.initManual(TypicalTimes.DAY);

        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        TrackedItem trackedItemToStop = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        TrackedItem startedTrackedItem = trackedItemToStop.startTimer();
        Project newExpectedProject = expectedProject.setTask(trackedItemToStop, startedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, newExpectedProject);

        StopCommand stopCommand = new StopTaskCommand(INDEX_FIRST, parentProject);
        String expectedMessage = String.format(StopCommand.MESSAGE_STOP_TIMER_SUCCESS,
                INDEX_FIRST.getOneBased(), "1 hr ");
        Project newProject = parentProject.setTask(trackedItemToStop, startedTrackedItem);
        model.setTrackedItem(parentProject, newProject);

        Clock.advance(1, ChronoUnit.HOURS);

        TrackedItem stoppedTrackedItem = startedTrackedItem.stopTimer();
        Project newStoppedProject = newExpectedProject.setTask(startedTrackedItem, stoppedTrackedItem);
        expectedModel.setTrackedItem(newExpectedProject, newStoppedProject);
        expectedModel.viewTasks(newStoppedProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(stopCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        StopCommand stopCommand = new StopTaskCommand(outOfBoundIndex, parentProject);

        assertCommandFailure(stopCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTimer_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        StopCommand stopCommand = new StopTaskCommand(INDEX_FIRST, parentProject);

        assertCommandFailure(stopCommand, model, StopCommand.MESSAGE_NO_TIMER_ERROR);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Clock.initManual(TypicalTimes.DAY);

        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        model.viewTasks(parentProject);

        Predicate<TrackedItem> predicate = showTaskAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemToStop = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        StopCommand stopCommand = new StopTaskCommand(INDEX_FIRST, parentProject);
        String expectedMessage = String.format(StopCommand.MESSAGE_STOP_TIMER_SUCCESS,
                INDEX_FIRST.getOneBased(), "1 hr ");

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project expectedProject = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        expectedModel.viewTasks(expectedProject);
        TrackedItem startedTrackedItem = trackedItemToStop.startTimer();
        Project startedExpectedProject = expectedProject.setTask(trackedItemToStop, startedTrackedItem);
        expectedModel.setTrackedItem(expectedProject, startedExpectedProject);

        Project startedProject = parentProject.setTask(trackedItemToStop, startedTrackedItem);
        model.setTrackedItem(parentProject, startedProject);

        Clock.advance(1, ChronoUnit.HOURS);

        TrackedItem stoppedTrackedItem = startedTrackedItem.stopTimer();
        Project stoppedExpectedProject = startedExpectedProject.setTask(startedTrackedItem, stoppedTrackedItem);
        expectedModel.setTrackedItem(startedExpectedProject, stoppedExpectedProject);
        expectedModel.viewTasks(stoppedExpectedProject);
        showTrackedItemWithName(expectedModel, stoppedTrackedItem.getName());
        expectedModel.commitToHistory();


        assertCommandSuccess(stopCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showProjectAtIndex(model, INDEX_FIRST);

        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < parentProject.getTaskList().size());

        StopCommand stopCommand = new StopTaskCommand(outOfBoundIndex, parentProject);

        assertCommandFailure(stopCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Project validProject = new ProjectBuilder().build();
        StopTaskCommand stopFirstCommand = new StopTaskCommand(INDEX_FIRST, validProject);
        StopTaskCommand stopSecondCommand = new StopTaskCommand(INDEX_SECOND, validProject);

        // same object -> returns true
        assertTrue(stopFirstCommand.equals(stopFirstCommand));

        // same values -> returns true
        StopCommand stopFirstCommandCopy = new StopTaskCommand(INDEX_FIRST, validProject);
        assertTrue(stopFirstCommand.equals(stopFirstCommandCopy));

        // different types -> returns false
        assertFalse(stopFirstCommand.equals(1));

        // null -> returns false
        assertFalse(stopFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(stopFirstCommand.equals(stopSecondCommand));
    }
}
