package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.momentum.logic.commands.AddProjectCommand.TEXT_PROJECT;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.TypicalProjects.ALICE;
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
import seedu.momentum.model.project.Task;
import seedu.momentum.model.project.TrackedItem;
import seedu.momentum.model.project.comparators.SortType;
import seedu.momentum.model.reminder.ReminderManager;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TaskBuilder;

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
                String.format(AddCommand.MESSAGE_SUCCESS, TEXT_PROJECT, validProject), expectedModel);
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
                String.format(AddCommand.MESSAGE_SUCCESS, TEXT_PROJECT, dana), expectedModel);
    }

    private void testShowReminder(AddCommand addCommand, String expectedReminder, int delay) throws CommandException,
            InterruptedException {
        ThreadWrapper.setIsRunningOnPlatform(false);

        addCommand.execute(model);

        Thread thread = new Thread(() -> assertEquals(expectedReminder, model.getReminder().get()));
        Thread.sleep(delay);
        thread.start();
    }

    @Test
    public void execute_addProjectCommand_showReminder() throws CommandException, InterruptedException {
        String dateTimeStr = Clock.now().plus(200, ChronoUnit.MILLIS).toString();
        Project project = new ProjectBuilder().withName("daesdaef").withReminder(dateTimeStr).build();

        AddCommand actualCommand = new AddProjectCommand(project);
        String expectedReminder = String.format(ReminderManager.PROJECT_REMINDER, project.getName());
        testShowReminder(actualCommand, expectedReminder, 1000);
    }

    @Test
    public void execute_addTaskCommand_showReminder() throws CommandException, InterruptedException {
        ThreadWrapper.setIsRunningOnPlatform(false);

        String dateTimeStr = Clock.now().plus(1500, ChronoUnit.MILLIS).toString();
        Project parentProject = ALICE;
        Task task = new TaskBuilder().withName("daesdaef").withReminder(dateTimeStr).build();

        AddCommand actualCommand = new AddTaskCommand(task, parentProject);

        String expectedReminder = String.format(ReminderManager.TASK_REMINDER, parentProject.getName(), task.getName());
        testShowReminder(actualCommand, expectedReminder, 3000);
    }

}
