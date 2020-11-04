package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.ThreadWrapper;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.testutil.ProjectBuilder;

public class DismissCommandTest {
    private static final DismissCommand dismissCommand = new DismissCommand();

    @Test
    public void execute_dismiss_successful() throws CommandException, InterruptedException {
        ThreadWrapper.setIsRunningOnPlatform(false);

        // trigger a reminder
        Model actualModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        String dateTimeStr = Clock.now().plus(200, ChronoUnit.MILLIS).toString();
        Project project = new ProjectBuilder().withName("daesdaef").withReminder(dateTimeStr).build();
        new AddProjectCommand(project).execute(actualModel);

        Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        expectedModel.removeReminderShown();

        Thread thread = new Thread(() ->
                assertCommandSuccess(dismissCommand, actualModel, DismissCommand.MESSAGE_SUCCESS, expectedModel));
        Thread.sleep(500);
        thread.start();
    }

    @Test
    public void execute_dismiss_throwsCommandException() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        assertCommandFailure(dismissCommand, model, DismissCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(dismissCommand.equals(dismissCommand));

        // same values -> returns true
        assertTrue(dismissCommand.equals(new DismissCommand()));

        // different types -> returns false
        assertFalse(dismissCommand.equals(1));

        // null -> returns false
        assertFalse(dismissCommand.equals(null));
    }
}
