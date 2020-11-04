package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.commons.core.Messages.MESSAGE_TEXT_PROJECT;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.ThreadWrapper;
import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.SortType;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectBook(), new UserPrefs());
    }

    @Test
    public void execute_newProject_success() {
        Project validProject = new ProjectBuilder().withName("TEST").build();

        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.addTrackedItem(validProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(new AddProjectCommand(validProject), model,
                String.format(AddCommand.MESSAGE_SUCCESS, MESSAGE_TEXT_PROJECT, validProject), expectedModel);
    }

    /**
     * Tests if add command places project in correct order.
     */
    @Test
    public void execute_addCommand_placesProjectInOrder() {
        Project dana = new ProjectBuilder().withName("Dana").build();
        AddCommand addDanaCommand = new AddProjectCommand(dana);

        // alphabetical order -> Dana gets placed in between Carl and Daniel
        Model expectedModel = new ModelManager(model.getProjectBook(), new UserPrefs());
        expectedModel.updateOrder(SortType.ALPHA, true);
        expectedModel.addTrackedItem(dana);
        expectedModel.commitToHistory();

        assertCommandSuccess(addDanaCommand, model,
                String.format(AddCommand.MESSAGE_SUCCESS, MESSAGE_TEXT_PROJECT, dana), expectedModel);
    }

    //    @Test
    //    public void execute_duplicateProject_throwsCommandException() {
    //        TrackedItem trackedItemInList = model.getProjectBook().getTrackedItemList().get(0);
    //        assertCommandFailure(
    //            new AddProjectCommand((Project) trackedItemInList), model, AddCommand.MESSAGE_DUPLICATE_ENTRY);
    //    }

    @Test
    public void execute_addCommand_showReminder() throws CommandException, InterruptedException {
        ThreadWrapper.setIsRunningOnPlatform(false);

        Project project = new ProjectBuilder()
                .withName("daesdaef")
                .withReminder(Clock.now().plus(1, ChronoUnit.SECONDS).toString()).build();
        AddCommand actualCommand = new AddProjectCommand(project);
        actualCommand.execute(model);
        String expectedReminder = String.format(ReminderManager.PROJECT_REMINDER, project.getName().toString());

        Thread thread = new Thread(() -> assertEquals(expectedReminder, model.getReminder().get()));
        thread.sleep(1000);
        thread.run();
    }

}
