package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Messages;
import seedu.momentum.commons.core.index.Index;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.TypicalTimes;

/**
 * Contains unit tests for {@code StartCommand}.
 */
public class StartCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Clock.initFixed(TypicalTimes.DAY);
        StartCommand startCommand = new StartCommand(INDEX_FIRST_PROJECT);

        Project projectToStart = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project startedProject = projectToStart.startTimer();
        expectedModel.setProject(projectToStart, startedProject);

        String expectedMessage =
                String.format(StartCommand.MESSAGE_START_TIMER_SUCCESS, INDEX_FIRST_PROJECT.getOneBased())
                + startedProject.getTimer().getStartTime().getFormatted();


        assertCommandSuccess(startCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        StartCommand startCommand = new StartCommand(outOfBoundIndex);

        assertCommandFailure(startCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_alreadyRunning_throwsCommandException() {
        StartCommand startCommand = new StartCommand(INDEX_FIRST_PROJECT);
        Project projectToStart = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        model.setProject(projectToStart, projectToStart.startTimer());

        assertCommandFailure(startCommand, model, StartCommand.MESSAGE_EXISTING_TIMER_ERROR);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Clock.initFixed(TypicalTimes.DAY);
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project projectToStart = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        Project startedProject = projectToStart.startTimer();

        StartCommand startCommand = new StartCommand(INDEX_FIRST_PROJECT);
        String expectedMessage =
                String.format(StartCommand.MESSAGE_START_TIMER_SUCCESS, INDEX_FIRST_PROJECT.getOneBased())
                        + startedProject.getTimer().getStartTime().getFormatted();

        showProjectAtIndex(expectedModel, INDEX_FIRST_PROJECT);
        assertCommandSuccess(startCommand, model, expectedMessage, expectedModel);
        Clock.reset();
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Index outOfBoundIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of project book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getProjectBook().getProjectList().size());

        StartCommand startCommand = new StartCommand(outOfBoundIndex);

        assertCommandFailure(startCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        StartCommand startFirstCommand = new StartCommand(INDEX_FIRST_PROJECT);
        StartCommand startSecondCommand = new StartCommand(INDEX_SECOND_PROJECT);

        // same object -> returns true
        assertTrue(startFirstCommand.equals(startFirstCommand));

        // same values -> returns true
        StartCommand startFirstCommandCopy = new StartCommand(INDEX_FIRST_PROJECT);
        assertTrue(startFirstCommand.equals(startFirstCommandCopy));

        // different types -> returns false
        assertFalse(startFirstCommand.equals(1));

        // null -> returns false
        assertFalse(startFirstCommand.equals(null));

        // different project -> returns false
        assertFalse(startFirstCommand.equals(startSecondCommand));
    }
}
