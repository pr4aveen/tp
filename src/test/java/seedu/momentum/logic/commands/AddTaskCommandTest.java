package seedu.momentum.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.momentum.testutil.Assert.assertThrows;
import static seedu.momentum.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.logic.commands.exceptions.CommandException;
import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;
import seedu.momentum.model.project.Project;
import seedu.momentum.model.project.Task;
import seedu.momentum.testutil.ProjectBuilder;
import seedu.momentum.testutil.TaskBuilder;

public class AddTaskCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null, new ProjectBuilder().build()));
    }

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(new TaskBuilder().build(), null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Task validTask = new TaskBuilder().build();

        AddCommand addCommand = new AddTaskCommand(validTask, parentProject);

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, AddTaskCommand.TEXT_TASK, validTask);

        Model expectedModel = new ModelManager(model.getProjectBook(), model.getUserPrefs());
        Project expectedParent = (Project) expectedModel.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Project newProject = expectedParent.addTask(validTask);
        expectedModel.setTrackedItem(expectedParent, newProject);
        expectedModel.viewTasks(newProject);
        expectedModel.commitToHistory();

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Project parentProject = (Project) model.getDisplayList().get(INDEX_FIRST.getZeroBased());
        Task validTask = (Task) parentProject.getTaskList().get(INDEX_FIRST.getZeroBased());
        Task duplicateTask = new TaskBuilder(validTask).build();
        AddTaskCommand addCommand = new AddTaskCommand(duplicateTask, parentProject);
        String expectedMessage = String.format(AddCommand.MESSAGE_DUPLICATE_ENTRY, AddTaskCommand.TEXT_TASK);
        assertThrows(CommandException.class,
                expectedMessage, () -> addCommand.execute(model));
    }

    @Test
    public void equals() {
        Project validProject = new ProjectBuilder().build();
        Task alice = new TaskBuilder().withName("Alice").build();
        Task bob = new TaskBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddTaskCommand(alice, validProject);
        AddCommand addBobCommand = new AddTaskCommand(bob, validProject);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddTaskCommand(alice, validProject);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different tasl -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
