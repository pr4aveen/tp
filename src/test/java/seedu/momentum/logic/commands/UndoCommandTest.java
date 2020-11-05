package seedu.momentum.logic.commands;

import static seedu.momentum.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.momentum.testutil.TypicalProjects.getTypicalProjectBook;

import org.junit.jupiter.api.Test;

import seedu.momentum.model.Model;
import seedu.momentum.model.ModelManager;
import seedu.momentum.model.UserPrefs;

/**
 * Contains integration tests with the Model and other commands.
 */
public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalProjectBook(), new UserPrefs());

    @Test
    public void execute_cannotUndoCommand_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_CANNOT_UNDO);
    }

    //    /**
    //     * The following tests test whether undo command is successful by checking expected message.
    //     * Specific modifications done to model is tested in ModelManagerTest as they cannot be tested here.
    //     */
    //    @Test
    //    public void execute_undoAddCommand_success() {
    //        UndoCommand undoCommand = new UndoCommand();
    //
    //        // Undoing the adding of a new project
    //        Project project = new ProjectBuilder().withName("TEST").build();
    //        AddCommand addProjectCommand = new AddProjectCommand(project);
    //
    //        try {
    //            addProjectCommand.execute(model);
    //        } catch (Exception e) {
    //            throw new AssertionError("This should not throw an exception.");
    //        }
    //        assertUndoCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS);
    //
    //        // Undoing the adding of a new task
    //        Task task = new TaskBuilder().withName("TEST").build();
    //        AddCommand addTaskCommand = new AddTaskCommand(task, project);
    //
    //        try {
    //            addProjectCommand.execute(model);
    //            addTaskCommand.execute(model);
    //        } catch (Exception e) {
    //            throw new AssertionError("This should not throw an exception.");
    //        }
    //        assertUndoCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS);
    //    }

}
