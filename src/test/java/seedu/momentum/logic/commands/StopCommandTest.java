package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains unit tests for {@code StartCommand}.
 */
public class StopCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Clock.initManual(TypicalTimes.DAY);

        TrackedItem trackedItemToStop = model.getDisplayList().get(INDEX_FIRST.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        TrackedItem startedTrackedItem = trackedItemToStop.startTimer();
        expectedModel.setTrackedItem(trackedItemToStop, startedTrackedItem);

        StopCommand stopCommand = new StopProjectCommand(INDEX_FIRST);
        String expectedMessage = String.format(StopCommand.MESSAGE_STOP_TIMER_SUCCESS,
                INDEX_FIRST.getOneBased(), 60);
        model.setTrackedItem(trackedItemToStop, startedTrackedItem);

        Clock.advance(1, ChronoUnit.HOURS);

        TrackedItem stoppedTrackedItem = startedTrackedItem.stopTimer();
        expectedModel.setTrackedItem(startedTrackedItem, stoppedTrackedItem);
        expectedModel.commitToHistory();

        assertCommandSuccess(stopCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayList().size() + 1);
        StopCommand stopCommand = new StopProjectCommand(outOfBoundIndex);

        assertCommandFailure(stopCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTimer_throwsCommandException() {
        StopCommand stopCommand = new StopProjectCommand(INDEX_FIRST);

        assertCommandFailure(stopCommand, model, StopCommand.MESSAGE_NO_TIMER_ERROR);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Clock.initManual(TypicalTimes.DAY);

        showProjectAtIndex(model, INDEX_FIRST);

        TrackedItem trackedItemToStop = model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        StopCommand stopCommand = new StopProjectCommand(INDEX_FIRST);
        String expectedMessage = String.format(StopCommand.MESSAGE_STOP_TIMER_SUCCESS,
                INDEX_FIRST.getOneBased(), 60);

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        TrackedItem startedTrackedItem = trackedItemToStop.startTimer();
        expectedModel.setTrackedItem(trackedItemToStop, startedTrackedItem);

        model.setTrackedItem(trackedItemToStop, startedTrackedItem);

        Clock.advance(1, ChronoUnit.HOURS);

        TrackedItem stoppedTrackedItem = startedTrackedItem.stopTimer();
        expectedModel.setTrackedItem(startedTrackedItem, stoppedTrackedItem);
        expectedModel.commitToHistory();

        showProjectAtIndex(expectedModel, INDEX_FIRST);

        assertCommandSuccess(stopCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showProjectAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectBook().getTrackedItemList().size());

        StopCommand stopCommand = new StopProjectCommand(outOfBoundIndex);

        assertCommandFailure(stopCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        StopProjectCommand stopFirstCommand = new StopProjectCommand(INDEX_FIRST);
        StopProjectCommand stopSecondCommand = new StopProjectCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(stopFirstCommand.equals(stopFirstCommand));

        // same values -> returns true
        StopCommand stopFirstCommandCopy = new StopProjectCommand(INDEX_FIRST);
        assertTrue(stopFirstCommand.equals(stopFirstCommandCopy));

        // different types -> returns false
        assertFalse(stopFirstCommand.equals(1));

        // null -> returns false
        assertFalse(stopFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(stopFirstCommand.equals(stopSecondCommand));
    }
}
