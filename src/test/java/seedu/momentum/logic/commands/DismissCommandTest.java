package seedu.momentum.logic.commands;

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
    @Test
    public void execute_dismiss_successful() throws CommandException, InterruptedException {
        ThreadWrapper.setIsRunningOnPlatform(false);

        // trigger a reminder
        Model expectedModel = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        String dateTimeStr = Clock.now().plus(200, ChronoUnit.MILLIS).toString();
        Project project = new ProjectBuilder().withName("daesdaef").withReminder(dateTimeStr).build();
        new AddProjectCommand(project).execute(expectedModel);

        // dismiss the reminder
        DismissCommand dismissCommand = new DismissCommand();

        Thread thread = new Thread(() ->
                assertCommandSuccess(dismissCommand, expectedModel, DismissCommand.MESSAGE_SUCCESS, expectedModel));
        Thread.sleep(500);
        thread.start();
    }

    @Test
    public void execute_dismiss_throwsCommandException() {
        Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
        DismissCommand dismissCommand = new DismissCommand();
        assertCommandFailure(dismissCommand, model, DismissCommand.MESSAGE_FAILURE);
    }
}
